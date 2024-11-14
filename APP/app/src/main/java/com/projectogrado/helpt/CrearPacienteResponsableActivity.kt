package com.projectogrado.helpt

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CrearPacienteResponsableActivity : AppCompatActivity() {

    private lateinit var etNombreResponsable: EditText
    private lateinit var etCorreoResponsable: EditText
    private lateinit var etDocumentoResponsable: EditText
    private lateinit var etTelefonoResponsable: EditText
    private lateinit var etFechaNacimientoResponsable: EditText
    private lateinit var etPasswordResponsable: EditText
    private lateinit var etNombrePaciente: EditText
    private lateinit var etDocumentoPaciente: EditText
    private lateinit var etFechaNacimientoPaciente: EditText
    private lateinit var btnGuardarResponsablePaciente: Button

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    // Variable para mantener el UID del doctor logueado
    private var doctorId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_responsable_paciente)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        // Obtener el UID del doctor logueado (antes de crear el responsable)
        doctorId = auth.currentUser?.uid

        // Inicializar los campos
        etNombreResponsable = findViewById(R.id.etNombreResponsable)
        etCorreoResponsable = findViewById(R.id.etCorreoResponsable)
        etDocumentoResponsable = findViewById(R.id.etDocumentoResponsable)
        etTelefonoResponsable = findViewById(R.id.etTelefonoResponsable)
        etFechaNacimientoResponsable = findViewById(R.id.etFechaNacimientoResponsable)
        etPasswordResponsable = findViewById(R.id.etPasswordResponsable)
        etNombrePaciente = findViewById(R.id.etNombrePaciente)
        etDocumentoPaciente = findViewById(R.id.etDocumentoPaciente)
        etFechaNacimientoPaciente = findViewById(R.id.etFechaNacimientoPaciente)
        btnGuardarResponsablePaciente = findViewById(R.id.btnGuardarResponsablePaciente)

        btnGuardarResponsablePaciente.setOnClickListener {
            guardarResponsableYPaciente()
        }
    }

    private fun guardarResponsableYPaciente() {
        val nombreResponsable = etNombreResponsable.text.toString()
        val correoResponsable = etCorreoResponsable.text.toString()
        val documentoResponsable = etDocumentoResponsable.text.toString()
        val telefonoResponsable = etTelefonoResponsable.text.toString()
        val fechaNacimientoResponsable = etFechaNacimientoResponsable.text.toString()
        val passwordResponsable = etPasswordResponsable.text.toString()

        val nombrePaciente = etNombrePaciente.text.toString()
        val documentoPaciente = etDocumentoPaciente.text.toString()
        val fechaNacimientoPaciente = etFechaNacimientoPaciente.text.toString()

        if (nombreResponsable.isNotEmpty() && correoResponsable.isNotEmpty() && passwordResponsable.isNotEmpty()) {
            // Crear responsable en Firebase Authentication
            auth.createUserWithEmailAndPassword(correoResponsable, passwordResponsable)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val responsableId = task.result?.user?.uid // UID del responsable

                        if (responsableId != null && doctorId != null) {
                            // Guardar datos del responsable en Firestore
                            val responsableData = hashMapOf(
                                "uid" to responsableId,
                                "nombre" to nombreResponsable,
                                "correo" to correoResponsable,
                                "documento" to documentoResponsable,
                                "telefono" to telefonoResponsable,
                                "fechaNacimiento" to fechaNacimientoResponsable,
                                "role" to "responsable"
                            )

                            firestore.collection("users").document(responsableId)
                                .set(responsableData)
                                .addOnSuccessListener {
                                    // Intentar guardar el paciente después de crear el responsable
                                    guardarPaciente(documentoPaciente, nombrePaciente, fechaNacimientoPaciente, responsableId)
                                }
                                .addOnFailureListener { e ->
                                    Log.e("FirestoreError", "Error al crear responsable: ${e.message}")
                                    Toast.makeText(this, "Error al crear responsable en Firestore", Toast.LENGTH_SHORT).show()
                                }
                        } else {
                            Log.e("Error", "responsableId o doctorId es null.")
                            Toast.makeText(this, "Error al obtener responsable o doctor.", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Log.e("AuthError", "Error al crear responsable: ${task.exception?.message}")
                        Toast.makeText(this, "Error al crear responsable: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(this, "Por favor, complete todos los campos del responsable", Toast.LENGTH_SHORT).show()
        }
    }

    private fun guardarPaciente(documentoPaciente: String, nombrePaciente: String, fechaNacimientoPaciente: String, responsableId: String) {
        if (documentoPaciente.isNotEmpty() && nombrePaciente.isNotEmpty() && fechaNacimientoPaciente.isNotEmpty()) {
            // Datos del paciente, incluyendo el UID del responsable y el doctor que lo creó (doctorId)
            val pacienteData = hashMapOf(
                "nombre" to nombrePaciente,
                "documento" to documentoPaciente,
                "fechaNacimiento" to fechaNacimientoPaciente,
                "responsableId" to responsableId,  // UID del responsable
                "doctorId" to doctorId  // UID del doctor logueado (correcto ahora)
            )

            firestore.collection("pacientes").document(documentoPaciente)
                .set(pacienteData)
                .addOnSuccessListener {
                    Toast.makeText(this, "Paciente creado correctamente", Toast.LENGTH_SHORT).show()

                    // Actualizar responsable con los datos del paciente
                    firestore.collection("users").document(responsableId)
                        .update("pacienteId", documentoPaciente)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Responsable y Paciente creados correctamente", Toast.LENGTH_SHORT).show()
                            finish()  // Cerrar la actividad
                        }
                        .addOnFailureListener { e ->
                            Log.e("FirestoreError", "Error al actualizar responsable con paciente: ${e.message}")
                            Toast.makeText(this, "Error al actualizar el responsable con el paciente", Toast.LENGTH_SHORT).show()
                        }
                }
                .addOnFailureListener { e ->
                    Log.e("FirestoreError", "Error al crear paciente: ${e.message}")
                    Toast.makeText(this, "Error al crear paciente en Firestore", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this, "Por favor, complete todos los campos del paciente", Toast.LENGTH_SHORT).show()
        }
    }
}