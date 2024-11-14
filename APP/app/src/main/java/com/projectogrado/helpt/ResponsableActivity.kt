package com.projectogrado.helpt

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class ResponsableActivity : AppCompatActivity() {

    private lateinit var btnVerInformacionPaciente: Button
    private lateinit var btnCalendarioCitas: Button
    private lateinit var btnVerResultados: Button
    private lateinit var btnChatDoctor: Button
    private lateinit var btnLogoutResponsable: Button

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_responsable)

        auth = FirebaseAuth.getInstance()

        // Inicializar botones
        btnVerInformacionPaciente = findViewById(R.id.btnVerInformacionPaciente)
        btnCalendarioCitas = findViewById(R.id.btnCalendarioCitas)
        btnVerResultados = findViewById(R.id.btnVerResultados)
        btnChatDoctor = findViewById(R.id.btnChatDoctor)
        btnLogoutResponsable = findViewById(R.id.btnLogoutResponsable)

        // Navegar a la pantalla de información del paciente
        btnVerInformacionPaciente.setOnClickListener {
            val intent = Intent(this, DetallePacienteActivity::class.java)
            intent.putExtra("responsableId", auth.currentUser?.uid) // Pasar el uid del responsable
            startActivity(intent)
        }

        // Navegar al calendario de citas
        btnCalendarioCitas.setOnClickListener {
            val intent = Intent(this, CalendarioActivity::class.java)
            startActivity(intent)
        }

        // Navegar a los resultados de las terapias
        btnVerResultados.setOnClickListener {
            val intent = Intent(this, ResultadosActivity::class.java)
            startActivity(intent)
        }

        // Navegar al chat con el doctor
        btnChatDoctor.setOnClickListener {
            val intent = Intent(this, ChatActivity::class.java)
            intent.putExtra("pacienteId", "paciente_id_ejemplo")  // Cambia esto para obtener el paciente asociado
            startActivity(intent)
        }

        // Cerrar sesión
        btnLogoutResponsable.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
