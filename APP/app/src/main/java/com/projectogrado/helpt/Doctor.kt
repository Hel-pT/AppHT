package com.projectogrado.helpt

data class Doctor(
    var nombre: String = "",
    var documento: String = "",
    var correo: String = "",
    var telefono: String = "",
    var role: String = "doctor",
    var documentId: String = ""  // Agregar el campo para almacenar el ID del documento en Firestore
)
