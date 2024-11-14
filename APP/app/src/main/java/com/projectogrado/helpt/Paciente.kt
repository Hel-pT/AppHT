package com.projectogrado.helpt

data class Paciente(
    var nombre: String = "",
    var documento: String = "",
    var fechaNacimiento: String = "",
    var responsableId: String = "",
    var doctorId: String = ""
) {
    // Constructor vacío necesario para Firestore
    constructor() : this("", "", "", "", "")
}
