package com.gabrieldev.appcomplect.data.repository

import com.gabrieldev.appcomplect.data.local.daos.IntentoDao
import com.gabrieldev.appcomplect.data.local.daos.UsuarioDao
import com.gabrieldev.appcomplect.model.InsigniaObtenida
import com.gabrieldev.appcomplect.model.Nivel
import com.google.firebase.Timestamp
import com.google.firebase.dataconnect.generated.DefaultConnector
import com.google.firebase.dataconnect.generated.execute
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Date
import java.util.UUID

class SyncRepository(
    private val usuarioDao: UsuarioDao,
    private val intentoDao: IntentoDao,
    private val connector: DefaultConnector,
    private val insigniaRepository: InsigniaRepository
) {

    suspend fun syncUsuario(): Nivel? = withContext(Dispatchers.IO) {
        val usuariosNoSync = usuarioDao.getUsuariosNoSincronizados()
        var nivelActualizado: Nivel? = null
        for (usuario in usuariosNoSync) {
            try {
                val uuid = UUID.fromString(usuario.id)
                connector.actualizarEstrellasUsuario.execute(
                    id = uuid,
                    nuevasEstrellas = usuario.estrellasPrestigio
                )
                usuarioDao.marcarComoSincronizado(usuario.id)

                val nivelResultado = verificarSubidaNivel(
                    usuarioUuid = uuid,
                    nuevasEstrellas = usuario.estrellasPrestigio,
                    nivelActualId = ""
                )
                if (nivelResultado != null) {
                    nivelActualizado = nivelResultado
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        nivelActualizado
    }

    suspend fun syncIntentos() = withContext(Dispatchers.IO) {
        val intentosNoSync = intentoDao.buscarIntentosNoSincronizados()
        for (intento in intentosNoSync) {
            try {
                connector.registrarIntentoExamen.execute(
                    usuarioId = UUID.fromString(intento.usuarioId),
                    archivoId = UUID.fromString(intento.archivoId),
                    calificacionObtenida = intento.calificacionObtenida,
                    fechaIntento = Timestamp(Date(intento.fechaIntento)),
                    completadoExitosamente = intento.completadoExitosamente
                )
                intentoDao.eliminarIntentoPorId(intento.id)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    suspend fun syncLogrosYNivel(
        usuarioId: String,
        estrellasActuales: Int,
        rachaActual: Int,
        nivelActualId: String
    ): Pair<List<InsigniaObtenida>, Nivel?> = withContext(Dispatchers.IO) {
        val insigniasNuevas = mutableListOf<InsigniaObtenida>()
        var nivelSubido: Nivel? = null

        try {
            val insigniasExamen = insigniaRepository.verificarInsignias(
                usuarioId = usuarioId,
                contexto = ContextoInsignia.Examen(calificacion = 0)
            )
            insigniasNuevas.addAll(insigniasExamen)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            val insigniasRacha = insigniaRepository.verificarInsignias(
                usuarioId = usuarioId,
                contexto = ContextoInsignia.Racha(rachaActualDias = rachaActual)
            )
            insigniasNuevas.addAll(insigniasRacha)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            nivelSubido = verificarSubidaNivel(
                usuarioUuid = UUID.fromString(usuarioId),
                nuevasEstrellas = estrellasActuales,
                nivelActualId = nivelActualId
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }

        Pair(insigniasNuevas, nivelSubido)
    }

    suspend fun syncAll(): Nivel? {
        syncIntentos()
        return syncUsuario()
    }

    private suspend fun verificarSubidaNivel(
        usuarioUuid: UUID,
        nuevasEstrellas: Int,
        nivelActualId: String
    ): Nivel? {
        return try {
            val sorted = connector.listarNiveles.execute().data.nivels
                .filter { it.jerarquia != null }
                .sortedBy { it.jerarquia }

            if (sorted.isEmpty()) return null

            val idxActual = if (nivelActualId.isBlank()) {
                0
            } else {
                sorted.indexOfFirst { it.id.toString() == nivelActualId }.coerceAtLeast(0)
            }

            var idxObjetivo = idxActual
            while (idxObjetivo < sorted.size - 1) {
                val nivelEval = sorted[idxObjetivo]
                if (nuevasEstrellas >= nivelEval.estrellasRequeridas) {
                    idxObjetivo++
                } else {
                    break
                }
            }

            if (idxObjetivo > idxActual) {
                val nivelNuevo = sorted[idxObjetivo]
                connector.actualizarNivelUsuario.execute(
                    id = usuarioUuid,
                    nivelId = nivelNuevo.id
                )
                Nivel(
                    id = nivelNuevo.id.toString(),
                    nombreRango = nivelNuevo.nombreRango,
                    jerarquia = nivelNuevo.jerarquia ?: 1,
                    estrellasRequeridas = nivelNuevo.estrellasRequeridas,
                    limitePalabrasTarjeta = nivelNuevo.limitePalabrasTarjeta
                )
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
