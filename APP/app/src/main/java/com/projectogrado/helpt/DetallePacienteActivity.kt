package com.projectogrado.helpt

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore


class DetallePacienteActivity : AppCompatActivity() {

    private lateinit var tvNombrePaciente: TextView
    private lateinit var tvDocumentoPaciente: TextView
    private lateinit var tvFechaNacimientoPaciente: TextView
    private lateinit var firestore: FirebaseFirestore
    private lateinit var keyunico: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_paciente)

        // Obtener las vistas
        tvNombrePaciente = findViewById(R.id.tvNombrePaciente)
        tvDocumentoPaciente = findViewById(R.id.tvDocumentoPaciente)
        tvFechaNacimientoPaciente = findViewById(R.id.tvFechaNacimientoPaciente)
        keyunico =findViewById(R.id.keyunico)


        val remss:String = intent.extras?.getString("key").orEmpty()

        keyunico.text = remss


        // Instanciar Firestore
        firestore = FirebaseFirestore.getInstance()


        firestore.collection("users").whereEqualTo("role", "responsable").get().addOnSuccessListener { documents ->
            if (!documents.isEmpty) {
                for (document in documents) {
                    val verdocu = document.data.get("pacienteId").toString()
                    verificaruser(verdocu)
                }
            }
        }
    }
    private fun verificaruser(idpaciente: String) {
        firestore.collection("pacientes").whereEqualTo("documento", idpaciente).get().addOnSuccessListener { documents ->
                    if (!documents.isEmpty) {

                        for (document in documents) {
                            tvNombrePaciente.text = "Paciente:\n"+document.data.get("nombre").toString()
                            tvDocumentoPaciente.text = "Documenteo:\n"+document.data.get("documento").toString()
                            tvFechaNacimientoPaciente.text = "Fecha de Nacimiento:\n"+document.data.get("fechaNacimiento").toString()

                        }

                    }
                    }


    }

}
