package com.projectogrado.helpt

import android.os.Bundle
import android.widget.CalendarView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class CalendarioActivity : AppCompatActivity() {

    private lateinit var calendarView: CalendarView
    private lateinit var tvEstadoCita: TextView
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendario)

        // Inicializar los elementos de la vista
        calendarView = findViewById(R.id.calendarView)
        tvEstadoCita = findViewById(R.id.tvEstadoCita)

        // Instanciar Firestore
        firestore = FirebaseFirestore.getInstance()



        // Establecer el listener para seleccionar una fecha
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = "$dayOfMonth/${month + 1}/$year" // Formato dd/MM/yyyy
            verificarCita(selectedDate)
        }
    }

    private fun verificarCita(fecha: String) {
        // Verificar en la colección "citas" si hay una cita para la fecha seleccionada
        firestore.collection("citas")
            .whereEqualTo("fecha", fecha)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    for (document in documents) {
                        tvEstadoCita.text = document.data.get("descripcion").toString()
                    }
                }
            }
            .addOnFailureListener {
                // En caso de error
                Toast.makeText(this, "Error al verificar las citas", Toast.LENGTH_SHORT).show()
            }
    }
}
