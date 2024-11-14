package com.projectogrado.helpt

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.util.*

class ChatActivity : AppCompatActivity() {

    private lateinit var recyclerViewChat: RecyclerView
  //  private lateinit var chatAdapter: ChatAdapter
    private lateinit var etMensaje: EditText
    private lateinit var btnEnviar: Button

    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var pacienteId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        recyclerViewChat = findViewById(R.id.recyclerViewChat)
        etMensaje = findViewById(R.id.etMensaje)
        btnEnviar = findViewById(R.id.btnEnviar)

        recyclerViewChat.layoutManager = LinearLayoutManager(this)

        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        pacienteId = intent.getStringExtra("pacienteId") ?: ""

        cargarMensajes()

        btnEnviar.setOnClickListener {
            enviarMensaje()
        }
    }

    private fun cargarMensajes() {
        val chatRef = firestore.collection("chats").document(pacienteId).collection("mensajes")
        chatRef.orderBy("timestamp", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    return@addSnapshotListener
                }

                if (snapshot != null && !snapshot.isEmpty) {
                    val listaMensajes = mutableListOf<Mensaje>()
                    for (document in snapshot.documents) {
                        val mensaje = document.toObject(Mensaje::class.java)
                        if (mensaje != null) {
                            listaMensajes.add(mensaje)
                        }
                    }

//                    chatAdapter = ChatAdapter(listaMensajes, auth.currentUser?.uid ?: "")
                    //                  recyclerViewChat.adapter = chatAdapter
                    //recyclerViewChat.scrollToPosition(listaMensajes.size - 1)
                }
            }
    }

    private fun enviarMensaje() {
        val mensaje = etMensaje.text.toString()

        if (mensaje.isNotEmpty()) {
            val chatRef = firestore.collection("chats").document(pacienteId).collection("mensajes")
            val nuevoMensaje = hashMapOf(
                "remitente" to (auth.currentUser?.uid ?: ""),
                "contenido" to mensaje,
                "timestamp" to Date()
            )

            chatRef.add(nuevoMensaje)
                .addOnSuccessListener {
                    etMensaje.text.clear()
                }
                .addOnFailureListener {
                    // Manejo de error
                }
        }
    }
}
