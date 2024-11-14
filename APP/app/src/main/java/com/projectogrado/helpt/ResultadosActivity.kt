package com.projectogrado.helpt

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class ResultadosActivity : AppCompatActivity() {

    private lateinit var recyclerViewResultados: RecyclerView
    private lateinit var resultadoAdapter: ResultadoAdapter
    private lateinit var firestore: FirebaseFirestore
    private lateinit var pacienteId: String // ID del paciente

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultados)

        recyclerViewResultados = findViewById(R.id.recyclerViewResultados)
        recyclerViewResultados.layoutManager = LinearLayoutManager(this)

        firestore = FirebaseFirestore.getInstance()

        // Supongamos que recibimos el ID del paciente por un intent
        pacienteId = intent.getStringExtra("pacienteId") ?: ""

        obtenerResultados()
    }

    private fun obtenerResultados() {
        firestore.collection("pacientes").document(pacienteId).collection("terapias")
            .get()
            .addOnSuccessListener { result ->
                val listaResultados = mutableListOf<Resultado>()
                for (document in result) {
                    val nombreTerapia = document.getString("nombre") ?: ""
                    val porcentajeProgreso = document.getDouble("progreso") ?: 0.0
                    listaResultados.add(Resultado(nombreTerapia, porcentajeProgreso))
                }

                // Configurar el adaptador
                resultadoAdapter = ResultadoAdapter(listaResultados)
                recyclerViewResultados.adapter = resultadoAdapter
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error al obtener resultados", Toast.LENGTH_SHORT).show()
            }
    }
}
