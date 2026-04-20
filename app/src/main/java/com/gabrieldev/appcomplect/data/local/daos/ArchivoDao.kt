package com.gabrieldev.appcomplect.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import androidx.room.Upsert
import com.gabrieldev.appcomplect.data.local.entidades.ArchivoEntity
import com.gabrieldev.appcomplect.data.local.entidades.CuestionarioEntity
import com.gabrieldev.appcomplect.data.local.entidades.PreguntaEntity
import com.gabrieldev.appcomplect.data.local.entidades.RespuestaEntity
import com.gabrieldev.appcomplect.data.local.entidades.TarjetaEntity
import com.gabrieldev.appcomplect.data.local.relaciones.ContenidoArchivoLocal

@Dao
interface ArchivoDao {

    @Upsert
    suspend fun guardarArchivo(archivo: ArchivoEntity)

    @Query("SELECT * FROM archivos")
    suspend fun listarArchivos(): ArchivoEntity?

    /**
     * verificar si el archivo ya existe en esta tabla para no duplicar datos
     * se utiliza cuando un usuarioB intente descarga un archivoX ya esixtente dentro de esta tabla
     * en ese caso, no se duplica el registro, simplemente a dicho usuario se le relaciona el archivo
     * dentro de la tabla descargas
     */
    @Query("SELECT EXISTS(SELECT 1 FROM archivos WHERE id = :idArchivo)")
    suspend fun existeArchivo(idArchivo: String): Boolean

    @Query("""
        SELECT archivos.* 
        FROM archivos 
        INNER JOIN descargas 
        ON archivos.id = descargas.archivo_id 
        WHERE descargas.usuario_id = :usuarioId
    """)
    suspend fun obtenerArchivosDelUsuario(usuarioId: String): List<ArchivoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun guardarTarjetas(tarjetas: List<TarjetaEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun guardarCuestionarios(cuestionarios: List<CuestionarioEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun guardarPreguntas(preguntas: List<PreguntaEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun guardarRespuestas(respuestas: List<RespuestaEntity>)

    @Transaction
    suspend fun guardarArchivoCompleto(
        archivo: ArchivoEntity,
        tarjetas: List<TarjetaEntity>,
        cuestionarios: List<CuestionarioEntity>,
        preguntas: List<PreguntaEntity>,
        respuestas: List<RespuestaEntity>
    ) {
        /**
         * Al usar @Transaction no se guarda nada a medias. Todo o nada.
         */
        guardarArchivo(archivo)
        guardarTarjetas(tarjetas)
        guardarCuestionarios(cuestionarios)
        guardarPreguntas(preguntas)
        guardarRespuestas(respuestas)
    }

    @Transaction
    @Query("SELECT * FROM archivos WHERE id = :idArchivo")
    suspend fun obtenerContenidoArchivoCompletoLocal(idArchivo: String): ContenidoArchivoLocal?
}