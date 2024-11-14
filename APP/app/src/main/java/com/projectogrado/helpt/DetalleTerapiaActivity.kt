package com.projectogrado.helpt

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetalleTerapiaActivity : AppCompatActivity() {

    private lateinit var tvNombreTerapia: TextView
    private lateinit var tvDescripcionTerapia: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_terapia)

        // Obtener las vistas del layout
        tvNombreTerapia = findViewById(R.id.tvNombreReto)
        tvDescripcionTerapia = findViewById(R.id.tvDescripcionTerapia)

        // Obtener los datos pasados desde la actividad anterior
        val nombreTerapia = intent.getStringExtra("nombreTerapia") ?: "Terapia"
        val descripcionTerapia = intent.getStringExtra("descripcionTerapia") ?: "Sin descripción"

        // Asignar los datos a las vistas
        tvNombreTerapia.text = nombreTerapia
        tvDescripcionTerapia.text = descripcionTerapia
    }
}
