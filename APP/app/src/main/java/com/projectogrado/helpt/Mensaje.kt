package com.projectogrado.helpt

import java.util.*

data class Mensaje(
    val remitente: String = "",
    val contenido: String = "",
    val timestamp: Date = Date()
)
