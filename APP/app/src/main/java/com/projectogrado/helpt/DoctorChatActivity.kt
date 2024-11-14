package com.projectogrado.helpt

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

class DoctorChatActivity : AppCompatActivity() {

    private lateinit var recyclerViewMensajes: RecyclerView
    //private lateinit var chatAdapter: ChatAdapter
    private lateinit var firestore: FirebaseFirestore
    private var mensajesListener: ListenerRegistration? = null
    private val mensajesList = mutableListOf<Mensaje>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_chat)

        recyclerViewMensajes = findViewById(R.id.recyclerViewMensajes)
        recyclerViewMensajes.layoutManager = LinearLayoutManager(this)

        firestore = FirebaseFirestore.getInstance()

        // Configurar adaptador de chat
        // chatAdapter = ChatAdapter(mensajesList)
        // recyclerViewMensajes.adapter = chatAdapter

        // Cargar mensajes desde Firestore
        cargarMensajes()
    }

    private fun cargarMensajes() {
        val pacienteId = intent.getStringExtra("pacienteId")
        if (pacienteId != null) {
            firestore.collection("chats")
                .document(pacienteId)
                .collection("mensajes")
                .orderBy("timestamp")
                .addSnapshotListener { snapshots, e ->
                    if (e != null) {
                        Log.e("DoctorChatActivity", "Error al cargar mensajes: ${e.message}")
                        Toast.makeText(this, "Error al cargar mensajes", Toast.LENGTH_SHORT).show()
                        return@addSnapshotListener
                    }

                    if (snapshots != null) {
                        mensajesList.clear()
                        for (document in snapshots.documents) {
                            val mensaje = document.toObject(Mensaje::class.java)
                            if (mensaje != null) {
                                mensajesList.add(mensaje)
                            }
                        }
                        //    chatAdapter.notifyDataSetChanged()
                        recyclerViewMensajes.scrollToPosition(mensajesList.size - 1)
                    }
                }
        } else {
            Toast.makeText(this, "ID de paciente no encontrado", Toast.LENGTH_SHORT).show()
            Log.e("DoctorChatActivity", "El ID de paciente es nulo")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mensajesListener?.remove()
    }
}
