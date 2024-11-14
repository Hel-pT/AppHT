package com.projectogrado.helpt

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var etEmailOrDocumento: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var tvForgotPassword: TextView

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etEmailOrDocumento = findViewById(R.id.etEmailOrDocumento)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        tvForgotPassword = findViewById(R.id.tvForgotPassword)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        btnLogin.setOnClickListener {
            val emailOrDocumento = etEmailOrDocumento.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (emailOrDocumento.isNotEmpty() && password.isNotEmpty()) {
                if (emailOrDocumento.contains("@")) {
                    // Si el input contiene un '@', es un correo, entonces tratamos con un responsable
                    loginResponsable(emailOrDocumento, password)
                } else {
                    // Si no contiene un '@', es el documento del paciente
                    loginPaciente(emailOrDocumento, password)
                }
            } else {
                Toast.makeText(this, "Por favor, ingrese email/documento y contraseña", Toast.LENGTH_SHORT).show()
            }
        }

        // Añadir funcionalidad para restablecer contraseña (solo para responsables)
        tvForgotPassword.setOnClickListener {
            val email = etEmailOrDocumento.text.toString().trim()

            if (email.isNotEmpty() && email.contains("@")) {
                resetPassword(email)
            } else {
                Toast.makeText(this, "Por favor, ingrese su correo electrónico", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loginResponsable(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Iniciar sesión como responsable
                    val userId = auth.currentUser?.uid
                    if (userId != null) {
                        checkUserRole(userId)
                    }
                } else {
                    Toast.makeText(this, "Datos incorrectos para responsable", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun loginPaciente(documento: String, password: String) {
        // Buscar al paciente en Firestore usando el documento
        firestore.collection("pacientes").document(documento).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val pacienteData = document.toObject(Paciente::class.java)
                    val documentoPaciente = pacienteData?.documento

                    // Comprobar si la contraseña es igual al documento
                    if (documentoPaciente == password) {
                        // Si es correcto, redirigir a la pantalla del paciente
                        Toast.makeText(this, "Bienvenido, ${pacienteData?.nombre}", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, PacienteActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Datos incorrectos para paciente", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Paciente no encontrado", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error al verificar paciente: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun checkUserRole(userId: String) {
        firestore.collection("users").document(userId).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val role = document.getString("role")
                    val userName = document.getString("nombre") ?: "Usuario"

                    when (role) {
                        "admin" -> {
                            Toast.makeText(this, "Bienvenido, $userName", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, AdminActivity::class.java))
                            finish()
                        }
                        "doctor" -> {
                            Toast.makeText(this, "Bienvenido, $userName", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, DoctorActivity::class.java))
                            finish()
                        }
                        "responsable" -> {
                            Toast.makeText(this, "Bienvenido, $userName", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, ResponsableActivity::class.java))
                            finish()
                        }
                        else -> {
                            Toast.makeText(this, "Rol no reconocido", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(this, "Usuario no encontrado en Firestore", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error al obtener rol del usuario: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun resetPassword(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Correo de restablecimiento de contraseña enviado", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Error al enviar correo: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
