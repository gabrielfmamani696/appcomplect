package com.gabrieldev.appcomplect.data.local.entidades

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "tarjetas",
    foreignKeys = [
        ForeignKey(
            entity = ArchivoEntity::class,
            parentColumns = ["id"], // id  en Archivo, la entidad relacionada
            childColumns = ["archivo_id"], // campo en esta entidad
            onDelete = ForeignKey.CASCADE // Si se borra el archivo, se borran sus tarjetas
        )
    ],
    indices = [Index(value = ["archivo_id"])]
)
data class TarjetaEntity(
    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "orden_secuencia")
    val ordenSecuencia: Int,

    @ColumnInfo(name = "contenido_texto")
    val contenidoTexto: String,

    @ColumnInfo(name = "tipo_fondo")
    val tipoFondo: String,

    @ColumnInfo(name = "data_fondo")
    val dataFondo: String,

    @ColumnInfo(name = "archivo_id")
    val archivoId: String
)