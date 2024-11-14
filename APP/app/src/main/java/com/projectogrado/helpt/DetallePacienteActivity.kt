package com.projectogrado.helpt

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetallePacienteActivity : AppCompatActivity() {

    private lateinit var tvNombrePaciente: TextView
    private lateinit var tvDocumentoPaciente: TextView
    private lateinit var tvFechaNacimientoPaciente: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_paciente)

        // Obtener las vistas
        tvNombrePaciente = findViewById(R.id.tvNombrePaciente)
        tvDocumentoPaciente = findViewById(R.id.tvDocumentoPaciente)
        tvFechaNacimientoPaciente = findViewById(R.id.tvFechaNacimientoPaciente)

        // Obtener los datos pasados desde la actividad anterior
        val nombrePaciente = intent.getStringExtra("nombrePaciente") ?: "Desconocido"
        val documentoPaciente = intent.getStringExtra("documentoPaciente") ?: "Desconocido"
        val fechaNacimientoPaciente = intent.getStringExtra("fechaNacimientoPaciente") ?: "Desconocido"

        // Asignar los datos a las vistas
        tvNombrePaciente.text = nombrePaciente
        tvDocumentoPaciente.text = documentoPaciente
        tvFechaNacimientoPaciente.text = fechaNacimientoPaciente
    }
}
