package com.gabrieldev.appcomplect.data.local.relaciones

import androidx.room.Embedded
import androidx.room.Relation
import com.gabrieldev.appcomplect.data.local.entidades.ArchivoEntity
import com.gabrieldev.appcomplect.data.local.entidades.CuestionarioEntity
import com.gabrieldev.appcomplect.data.local.entidades.TarjetaEntity

data class ContenidoArchivoLocal(
    @Embedded
    val archivo: ArchivoEntity,

    @Relation(
     parentColumn = "id",
     entityColumn = "archivo_id"
    )
    val tarjetas: List<TarjetaEntity>,

    @Relation(
     entity = CuestionarioEntity::class,
     parentColumn = "id",
     entityColumn = "archivo_id"
    )
    val cuestionarios: List<CuestionarioPreguntas>
)