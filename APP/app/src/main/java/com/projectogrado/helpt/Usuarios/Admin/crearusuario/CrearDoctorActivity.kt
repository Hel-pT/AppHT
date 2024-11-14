package com.projectogrado.helpt.Usuarios.Admin.crearusuario

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.projectogrado.helpt.R

class CrearDoctorActivity : AppCompatActivity() {

    private lateinit var etNombreDoctor: EditText
    private lateinit var etDocumentoDoctor: EditText
    private lateinit var etCorreoDoctor: EditText
    private lateinit var etPasswordDoctor: EditText
    private lateinit var etTelefonoDoctor: EditText
    private lateinit var btnCrearDoctor: Button

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_doctor)

        // Inicializar Firebase Authentication y Firestore
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        // Vincular las vistas con sus IDs
        etNombreDoctor = findViewById(R.id.etNombreDoctor)
        etDocumentoDoctor = findViewById(R.id.etDocumentoDoctor)
        etCorreoDoctor = findViewById(R.id.etCorreoDoctor)
        etPasswordDoctor = findViewById(R.id.etPasswordDoctor)
        etTelefonoDoctor = findViewById(R.id.etTelefonoDoctor)
        btnCrearDoctor = findViewById(R.id.btnCrearDoctor)

        // Configurar el botón de creación del doctor
        btnCrearDoctor.setOnClickListener {
            crearDoctor()
        }
    }

    private fun crearDoctor() {
        // Datos del doctor
        val nombreDoctor = etNombreDoctor.text.toString()
        val documentoDoctor = etDocumentoDoctor.text.toString()
        val correoDoctor = etCorreoDoctor.text.toString() // Correo del doctor
        val passwordDoctor = etPasswordDoctor.text.toString()
        val telefonoDoctor = etTelefonoDoctor.text.toString()

        if (nombreDoctor.isNotEmpty() && documentoDoctor.isNotEmpty() &&
            correoDoctor.isNotEmpty() && passwordDoctor.isNotEmpty() && telefonoDoctor.isNotEmpty()) {

            // Registrar el doctor en Firebase Authentication
            auth.createUserWithEmailAndPassword(correoDoctor, passwordDoctor)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val userId = auth.currentUser?.uid

                        // Guardar detalles del doctor en Firestore
                        if (userId != null) {
                            guardarDoctorEnFirestore(userId, nombreDoctor, documentoDoctor, correoDoctor, telefonoDoctor)
                        }
                    } else {
                        Toast.makeText(this, "Error al crear doctor: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun guardarDoctorEnFirestore(userId: String, nombre: String, documento: String, correo: String, telefono: String) {
        val doctor = hashMapOf(
            "nombre" to nombre,
            "documento" to documento,
            "correo" to correo,
            "telefono" to telefono,
            "role" to "doctor" // Rol predeterminado
        )

        firestore.collection("users").document(userId).set(doctor)
            .addOnSuccessListener {
                Toast.makeText(this, "Doctor creado correctamente", Toast.LENGTH_SHORT).show()
                finish()  // Finalizar la actividad
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error al guardar en Firestore: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
