package com.gabrieldev.appcomplect.data.local.entidades

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "descargas",
    primaryKeys = ["usuario_id", "archivo_id"],
    foreignKeys = [
        ForeignKey(
            entity = UsuarioEntity::class,
            parentColumns = ["id"],
            childColumns = ["usuario_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ArchivoEntity::class,
            parentColumns = ["id"],
            childColumns = ["archivo_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["archivo_id"])]
)
data class DescargaEntity(
    @ColumnInfo(name = "usuario_id")
    val usuarioId: String,

    @ColumnInfo(name = "archivo_id")
    val archivoId: String
)