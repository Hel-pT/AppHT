package com.projectogrado.helpt

import android.os.Bundle
import android.widget.CalendarView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class CalendarioActivity : AppCompatActivity() {

    private lateinit var calendarView: CalendarView
    private lateinit var tvEstadoCita: TextView
    private lateinit var tv1: TextView
    private lateinit var tv2: TextView
    private lateinit var tv3: TextView
    private lateinit var firestore: FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendario)

        // Inicializar los elementos de la vista
        calendarView = findViewById(R.id.calendarView)
        tvEstadoCita = findViewById(R.id.tvEstadoCita)
        tv1 = findViewById(R.id.tv1)
        tv2 = findViewById(R.id.tv2)
        tv3 = findViewById(R.id.tv3)

        // Instanciar Firestore
        firestore = FirebaseFirestore.getInstance()

        tv1.text = ""
        tv2.text = ""
        tv3.text = ""

        // Establecer el listener para seleccionar una fecha



        firestore.collection("users").whereEqualTo("role", "responsable").get().addOnSuccessListener { documents ->
            if (!documents.isEmpty) {
                for (document in documents) {
                    val verdocu = document.data.get("pacienteId").toString()
                    calendarView.setOnDateChangeListener { _, dayOfMonth, month, year ->
                        val selectedDate = "$year/${month + 1}/$dayOfMonth" // Formato dd/MM/yyyy
                        verificarCita(selectedDate, verdocu)

                    }


                }
            }
        }


    }


    private fun verificarCita(fecha: String, idpaciente: String) {
        // Verificar en la colección "citas" si hay una cita para la fecha seleccionada
        firestore.collection("citas")
            .whereEqualTo("fecha", fecha)
            .whereEqualTo("pacienteId", idpaciente)
            .get().addOnSuccessListener { documents ->
            if (!documents.isEmpty) {

                for (document in documents) {
                    tvEstadoCita.text = "Cita asignada para:\n"+document.data.get("fecha").toString()
                    tv1.text = "ID paciente:\n"+document.data.get("pacienteId").toString()
                    tv2.text = "Descripcion:\n"+document.data.get("descripcion").toString()
                    tv3.text = "Duracion:\n"+ document.data.get("duracion").toString() +" horas"
                }
            } else {
                // No se encontró cita
                tvEstadoCita.text = "No hay cita programada para el $fecha"
                tv1.text = ""
                tv2.text = ""
                tv3.text = ""
            }
        }
        .addOnFailureListener {
            // En caso de error
            Toast.makeText(this, "Error al verificar las citas", Toast.LENGTH_SHORT).show()
        }
    }
}
