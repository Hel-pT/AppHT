package com.projectogrado.helpt

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class PacienteActivity : AppCompatActivity() {

    private lateinit var cardTerapias: CardView
    private lateinit var cardResponsable: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paciente)

        // Referencias a los CardView
        cardTerapias = findViewById(R.id.cardTerapias)
        cardResponsable = findViewById(R.id.cardResponsable)

        // Acciones cuando se hace clic en las tarjetas
        cardTerapias.setOnClickListener {
            // Redirigir a la actividad de Terapias
            val intent = Intent(this, ListaTerapiasActivity::class.java)
            startActivity(intent)
        }

        // Acciones cuando se hace clic en las tarjetas
        cardResponsable.setOnClickListener {
            // Redirigir a la actividad de Terapias
            val intent = Intent(this, ListaRecompensasActivity::class.java)
            startActivity(intent)
        }

    }
}
