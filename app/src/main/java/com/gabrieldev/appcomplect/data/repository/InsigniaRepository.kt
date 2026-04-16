package com.gabrieldev.appcomplect.data.repository

import com.gabrieldev.appcomplect.model.InsigniaObtenida
import com.gabrieldev.appcomplect.model.InsigniaSlug
import com.google.firebase.Timestamp
import com.google.firebase.dataconnect.generated.DefaultConnector
import com.google.firebase.dataconnect.generated.execute
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import java.util.UUID

sealed class ContextoInsignia {
    data class Examen(
        val calificacion: Int
    ) : ContextoInsignia()

    data class Racha(
        val rachaActualDias: Int
    ) : ContextoInsignia()

    object ArticuloCompletado : ContextoInsignia()
}

class InsigniaRepository(private val connector: DefaultConnector) {

    suspend fun verificarInsignias(
        usuarioId: String,
        contexto: ContextoInsignia
    ): List<InsigniaObtenida> {
        return try {
            val uuid = UUID.fromString(usuarioId)

            val todasInsignias = connector.listarTodasInsignias.execute().data.insignias

            val yaNotificadas = connector.obtenerInsigniasNotificadas
                .execute(usuarioId = uuid)
                .data
                .logroNotificados
                .mapNotNull { it.insignia?.condicionDesbloqueo }
                .toSet()

            val slugsCandidatos = mutableSetOf<String>()

            when (contexto) {
                is ContextoInsignia.ArticuloCompletado -> {
                    if (InsigniaSlug.PRIMER_ARTICULO !in yaNotificadas) {
                        slugsCandidatos.add(InsigniaSlug.PRIMER_ARTICULO)
                    }
                }
                is ContextoInsignia.Examen -> {

                    if (InsigniaSlug.PRIMER_CUESTIONARIO !in yaNotificadas) {
                        slugsCandidatos.add(InsigniaSlug.PRIMER_CUESTIONARIO)
                    }

                    if (contexto.calificacion == 100 && InsigniaSlug.PERFECCIONISTA !in yaNotificadas) {
                        slugsCandidatos.add(InsigniaSlug.PERFECCIONISTA)
                    }
                }
                is ContextoInsignia.Racha -> {
                    evaluarRacha(contexto.rachaActualDias, yaNotificadas, slugsCandidatos)
                }
            }

            if (slugsCandidatos.isEmpty()) return emptyList()

            val insigniasElegibles = todasInsignias
                .filter { it.condicionDesbloqueo in slugsCandidatos }

            if (insigniasElegibles.isEmpty()) return emptyList()

            val ahora = Timestamp(Date())
            val insigniasRegistradas = mutableListOf<UUID>()
            insigniasElegibles.forEach { ins ->
                try {
                    connector.registrarLogroNotificado.execute(
                        usuarioId = uuid,
                        insigniaId = ins.id
                    )
                    insigniasRegistradas.add(ins.id)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            if (insigniasRegistradas.isEmpty()) return emptyList()

            val insigniasNuevas = insigniasElegibles
                .filter { it.id in insigniasRegistradas }
                .map { ins ->
                    InsigniaObtenida(
                        id = ins.id.toString(),
                        nombreVisible = ins.nombreVisible,
                        descripcion = ins.descripcion,
                        iconoRef = ins.iconoRef,
                        condicionDesbloqueo = ins.condicionDesbloqueo,
                        fechaNotificacion = formatearFechaNotificacion(ahora)
                    )
                }

            insigniasNuevas
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    suspend fun obtenerInsigniasObtenidas(usuarioId: String): List<InsigniaObtenida> {
        return try {
            val uuid = UUID.fromString(usuarioId)
            connector.listarInsigniasObtenidasUsuario
                .execute(usuarioId = uuid)
                .data
                .logroNotificados
                .mapNotNull { logro ->
                    logro.insignia?.let { insignia ->
                        InsigniaObtenida(
                            id = insignia.id.toString(),
                            nombreVisible = insignia.nombreVisible,
                            descripcion = insignia.descripcion,
                            iconoRef = insignia.iconoRef,
                            condicionDesbloqueo = insignia.condicionDesbloqueo,
                            fechaNotificacion = formatearFechaNotificacion(logro.fechaNotificacion)
                        )
                    }
                }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    private fun formatearFechaNotificacion(fecha: Timestamp?): String {
        return try {
            if (fecha == null) return "Fecha desconocida"
            val millis = fecha.seconds * 1000 + fecha.nanoseconds / 1_000_000
            val date = Date(millis)
            val formatter = SimpleDateFormat("HH:mm dd-MM-yyyy", Locale.getDefault())
            formatter.timeZone = TimeZone.getDefault()
            formatter.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
            "Fecha desconocida"
        }
    }

    private fun evaluarRacha(
        rachaActualDias: Int,
        yaNotificadas: Set<String>,
        candidatos: MutableSet<String>
    ) {
        if (rachaActualDias >= 1 && InsigniaSlug.RACHA_1_DIA !in yaNotificadas)
            candidatos.add(InsigniaSlug.RACHA_1_DIA)
        if (rachaActualDias >= 3 && InsigniaSlug.RACHA_3_DIAS !in yaNotificadas)
            candidatos.add(InsigniaSlug.RACHA_3_DIAS)
        if (rachaActualDias >= 7 && InsigniaSlug.RACHA_7_DIAS !in yaNotificadas)
            candidatos.add(InsigniaSlug.RACHA_7_DIAS)
    }
}
