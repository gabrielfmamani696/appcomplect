package com.gabrieldev.appcomplect.data.local.entidades

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class UsuarioEntity (

    @PrimaryKey
    val id: String = "",

    @ColumnInfo(name = "alias")
    val alias: String = "",

    @ColumnInfo(name = "estrellas_prestigio")
    val estrellasPrestigio: Int = 0,

    @ColumnInfo(name = "racha_actual_dias")
    val rachaActualDias: Int = 0,

    @ColumnInfo(name = "ultima_actividad")
    val ultimaActividad: String = "",

    @ColumnInfo(name = "rol")
    val rol: String = "",

    @ColumnInfo(name = "hora_notificacion")
    val horaNotificacion: String = "",

//    @ColumnInfo(name = "numero_celular")
//    val numeroCelular: String = "",

//    @ColumnInfo(name = "curso")
//    val curso: String = "",

//    @ColumnInfo(name = "paralelo")
//    val paralelo: String = "",
//
//    @ColumnInfo(name = "nombre_colegio")
//    val nombreColegio: String = "",

    @ColumnInfo(name = "nombre")
    val nombre: String = "",

//    @ColumnInfo(name = "apellido_paterno")
//    val apellidoPaterno: String = "",
//
//    @ColumnInfo(name = "apellido_materno")
//    val apellidoMaterno: String = "",

//    en la logica local nivel no importa
//    @ColumnInfo(name = "nivel")
//    val nivel: String = "",

    // false: Creado offline, pendiente de subir al servidor
    // true: Ya está en el servidor
    @ColumnInfo(name = "sincronizado")
    val sincronizado: Boolean
)
