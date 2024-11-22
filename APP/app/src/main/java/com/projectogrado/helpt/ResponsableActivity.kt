package com.projectogrado.helpt


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth



class ResponsableActivity : AppCompatActivity() {

    private lateinit var btnVerInformacionPaciente: Button
    private lateinit var btnCalendarioCitas: Button
    private lateinit var btnVerResultados: Button
    private lateinit var btnChatDoctor: Button
    private lateinit var btnLogoutResponsable: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var tvdoc: TextView



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
        tvdoc = findViewById(R.id.tvdoc)

        val remss:String = intent.extras?.getString("key").orEmpty()

        tvdoc.text = remss

        // Navegar a la pantalla de información del paciente
        btnVerInformacionPaciente.setOnClickListener {
            val mss:String = tvdoc.text.toString()
            val intent = Intent(this, DetallePacienteActivity::class.java)
            intent.putExtra("key", mss)
            startActivity(intent)
        }

        // Navegar al calendario de citas
        btnCalendarioCitas.setOnClickListener {
            val mss:String = tvdoc.text.toString()
            val intent = Intent(this, CalendarioActivity::class.java)
            intent.putExtra("key", mss)
            /*

            val sendmss = Intent(this, ResponsableActivity::class.java)
            sendmss.putExtra("key", mss)
            startActivity(sendmss)
             */
            startActivity(intent)
        }

        // Navegar a los resultados de las terapias
        btnVerResultados.setOnClickListener {
            val mss:String = tvdoc.text.toString()
            val intent = Intent(this, ResultadosActivity::class.java)
            intent.putExtra("key", mss)
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
            val mss:String = tvdoc.text.toString()
            auth.signOut()
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("key", mss)
            startActivity(intent)
            finish()
        }
    }
}
