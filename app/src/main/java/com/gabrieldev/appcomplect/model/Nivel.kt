package com.gabrieldev.appcomplect.model

data class Nivel(
    val id: String,
    val nombreRango: String,
    val jerarquia: Int = 1,
    val estrellasRequeridas: Int,
    val limitePalabrasTarjeta: Int
)
