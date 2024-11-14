package com.projectogrado.helpt.Usuarios.Admin.adminsitrarusuario

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.projectogrado.helpt.Doctor
import com.projectogrado.helpt.R

class ModificarDoctorActivity : AppCompatActivity() {

    private lateinit var etNombreDoctor: EditText
    private lateinit var etDocumentoDoctor: EditText
    private lateinit var etCorreoDoctor: EditText
    private lateinit var etTelefonoDoctor: EditText
    private lateinit var btnGuardarCambios: Button

    private lateinit var firestore: FirebaseFirestore
    private var doctorId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modificar_doctor)

        // Inicializar las vistas
        etNombreDoctor = findViewById(R.id.etNombreDoctor)
        etDocumentoDoctor = findViewById(R.id.etDocumentoDoctor)
        etCorreoDoctor = findViewById(R.id.etCorreoDoctor)
        etTelefonoDoctor = findViewById(R.id.etTelefonoDoctor)
        btnGuardarCambios = findViewById(R.id.btnGuardarCambios)

        firestore = FirebaseFirestore.getInstance()

        // Obtener el ID del doctor desde el intent
        doctorId = intent.getStringExtra("doctorId")

        // Si el ID del doctor no es nulo, cargar los datos
        if (doctorId != null) {
            cargarDatosDoctor(doctorId!!)
        } else {
            Toast.makeText(this, "ID de doctor no proporcionado", Toast.LENGTH_SHORT).show()
            finish() // Si no se pasa un ID de doctor, cierra la actividad
        }

        // Manejar el evento de clic del botón para guardar cambios
        btnGuardarCambios.setOnClickListener {
            if (doctorId != null) {
                guardarCambiosDoctor(doctorId!!)
            }
        }
    }

    private fun cargarDatosDoctor(doctorId: String) {
        firestore.collection("users").document(doctorId).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    // Convertir el documento en un objeto Doctor y llenar los campos
                    val doctor = document.toObject(Doctor::class.java)
                    if (doctor != null) {
                        etNombreDoctor.setText(doctor.nombre)
                        etDocumentoDoctor.setText(doctor.documento)
                        etCorreoDoctor.setText(doctor.correo)
                        etTelefonoDoctor.setText(doctor.telefono)

                        // Bloquear el campo de correo electrónico para que no se pueda modificar
                        etCorreoDoctor.isEnabled = false
                    } else {
                        Toast.makeText(this, "Error al convertir los datos del doctor", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Doctor no encontrado", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { e ->
                Log.e("FirestoreError", "Error al obtener los datos del doctor: ${e.message}")
                Toast.makeText(this, "Error al obtener los datos del doctor", Toast.LENGTH_SHORT).show()
            }
    }

    private fun guardarCambiosDoctor(doctorId: String) {
        val nombre = etNombreDoctor.text.toString()
        val documento = etDocumentoDoctor.text.toString()
        val telefono = etTelefonoDoctor.text.toString()

        val doctorActualizado = mapOf(
            "nombre" to nombre,
            "documento" to documento,
            "telefono" to telefono
        )

        firestore.collection("users").document(doctorId)
            .update(doctorActualizado)
            .addOnSuccessListener {
                Toast.makeText(this, "Doctor actualizado correctamente", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener { e ->
                Log.e("FirestoreError", "Error al actualizar el doctor: ${e.message}")
                Toast.makeText(this, "Error al actualizar el doctor", Toast.LENGTH_SHORT).show()
            }
    }
}
