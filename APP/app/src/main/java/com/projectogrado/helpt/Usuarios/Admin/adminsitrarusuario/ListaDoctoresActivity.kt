package com.projectogrado.helpt.Usuarios.Admin.adminsitrarusuario

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.projectogrado.helpt.Doctor
import com.projectogrado.helpt.R

class ListaDoctoresActivity : AppCompatActivity() {

    private lateinit var recyclerViewDoctores: RecyclerView
    private lateinit var doctorAdapter: DoctorAdapter
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_doctores)

        recyclerViewDoctores = findViewById(R.id.recyclerViewDoctores)
        recyclerViewDoctores.layoutManager = LinearLayoutManager(this)

        firestore = FirebaseFirestore.getInstance()

        // Obtener doctores de Firestore
        obtenerDoctores()
    }

    private fun obtenerDoctores() {
        firestore.collection("users")
            .whereEqualTo("role", "doctor") // Filtrar solo doctores
            .get()
            .addOnSuccessListener { result ->
                Log.d("FirestoreSuccess", "Consulta exitosa. Documentos obtenidos: ${result.size()}")
                val doctorList = mutableListOf<Doctor>()

                // Si no hay documentos, mostrar mensaje y salir
                if (result.isEmpty) {
                    Log.d("FirestoreEmpty", "No se encontraron doctores en Firestore.")
                    Toast.makeText(this, "No se encontraron doctores.", Toast.LENGTH_SHORT).show()
                    return@addOnSuccessListener
                }

                for (document in result) {
                    try {
                        // Comprobamos si los campos existen en el documento antes de asignarlos
                        val doctor = document.toObject(Doctor::class.java).apply {
                            documentId = document.id
                        }
                        Log.d("FirestoreDoctor", "Doctor encontrado: ${doctor.nombre}")
                        doctorList.add(doctor)
                    } catch (e: Exception) {
                        Log.e("FirestoreError", "Error al convertir documento a Doctor: ${e.message}")
                        Toast.makeText(this, "Error al procesar un doctor: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }

                // Si la lista está vacía después de obtener los datos
                if (doctorList.isEmpty()) {
                    Log.d("DoctorListEmpty", "La lista de doctores está vacía.")
                    Toast.makeText(this, "No se pudieron cargar los doctores.", Toast.LENGTH_SHORT).show()
                } else {
                    // Configurar el adaptador
                    doctorAdapter = DoctorAdapter(doctorList) { documentId ->
                        // Ir a la vista de modificación del doctor, pasando el ID del documento
                        val intent = Intent(this, ModificarDoctorActivity::class.java)
                        intent.putExtra("doctorId", documentId) // Pasar el documentId del doctor
                        startActivity(intent)
                    }
                    recyclerViewDoctores.adapter = doctorAdapter
                }
            }
            .addOnFailureListener { exception ->
                Log.e("FirestoreError", "Error al obtener doctores: ${exception.message}")
                Toast.makeText(this, "Error al obtener doctores: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
