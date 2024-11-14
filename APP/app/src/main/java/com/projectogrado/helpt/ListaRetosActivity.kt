package com.projectogrado.helpt

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class ListaRecompensasActivity : AppCompatActivity() {
    private lateinit var recyclerViewRecompensas: RecyclerView
    private lateinit var recompensaAdapter: RetosAdapter
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_retos)

        recyclerViewRecompensas = findViewById(R.id.recyclerViewRecompensas)
        recyclerViewRecompensas.layoutManager = LinearLayoutManager(this)

        firestore = FirebaseFirestore.getInstance()

        obtenerRecompensas()
    }

    private fun obtenerRecompensas() {
        firestore.collection("recompensas")
            .get()
            .addOnSuccessListener { result ->
                val recompensaList = mutableListOf<Retos>()
                for (document in result) {
                    val recompensa = document.toObject(Retos::class.java)
                    recompensaList.add(recompensa)
                }

                recompensaAdapter = RetosAdapter(recompensaList) { recompensa ->
                    // Al hacer clic en una recompensa, abrir la pantalla de detalles
                    val intent = Intent(this, DetalleTerapiaActivity::class.java)
                    intent.putExtra("nombreReto", recompensa.nombre)
                    startActivity(intent)
                }

                recyclerViewRecompensas.adapter = recompensaAdapter
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error al obtener recompensas: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }
}