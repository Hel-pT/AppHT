package com.projectogrado.helpt


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class ListaTerapiasActivity : AppCompatActivity() {

    private lateinit var recyclerViewTerapias: RecyclerView
    private lateinit var terapiaAdapter: TerapiaAdapter
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_terapias)

        recyclerViewTerapias = findViewById(R.id.recyclerViewTerapias)
        recyclerViewTerapias.layoutManager = LinearLayoutManager(this)

        firestore = FirebaseFirestore.getInstance()

        // Obtener terapias de Firestore
        obtenerTerapias()
    }

    private fun obtenerTerapias() {
        firestore.collection("terapias")
            .get()
            .addOnSuccessListener { result ->
                val listaTerapias = mutableListOf<Terapia>()
                for (document in result) {
                    val terapia = document.toObject(Terapia::class.java)
                    listaTerapias.add(terapia)
                }

                // Configurar el adaptador con la lista de terapias
                terapiaAdapter = TerapiaAdapter(listaTerapias) { terapia ->
                    // Al hacer clic en una terapia, abrir la pantalla de detalles
                    val intent = Intent(this, DetalleTerapiaActivity::class.java)
                    intent.putExtra("nombreTerapia", terapia.nombre)
                    intent.putExtra("descripcionTerapia", terapia.descripcion)
                    startActivity(intent)
                }

                recyclerViewTerapias.adapter = terapiaAdapter
            }
            .addOnFailureListener {
                // Manejar el error si no se pueden obtener las terapias
            }
    }
}
