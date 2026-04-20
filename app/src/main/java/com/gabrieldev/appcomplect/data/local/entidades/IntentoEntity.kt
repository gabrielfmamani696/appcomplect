package com.gabrieldev.appcomplect.data.local.entidades

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "intentos",
    foreignKeys = [
        ForeignKey(
            entity = UsuarioEntity::class,
            parentColumns = ["id"],
            childColumns = ["usuario_id"]
        ),
        ForeignKey(
            entity = ArchivoEntity::class,
            parentColumns = ["id"], // id  en archivoEntity
            childColumns = ["archivo_id"], // campo en esta entidad
        )
    ],
    indices = [
        Index(value = ["archivo_id"]),
        Index(value = ["usuario_id"]),
    ]
)
data class IntentoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    
    @ColumnInfo(name = "calificacion_obtenida")
    val calificacionObtenida: Int,
    
    @ColumnInfo(name = "fecha_intento")
    val fechaIntento: Long = System.currentTimeMillis(),
    
    @ColumnInfo(name = "completado_exitosamente")
    val completadoExitosamente: Boolean,
    
    // false: Creado offline, pendiente de subir al servidor
    // true: Ya está en el servidor
    @ColumnInfo(name = "sincronizado")
    val sincronizado: Boolean = false,

    @ColumnInfo(name = "archivo_id")
    val archivoId: String, //pertenece a x archivo

    @ColumnInfo(name = "usuario_id")
    val usuarioId: String //pertenece a x usuario
)