package com.gabrieldev.appcomplect.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gabrieldev.appcomplect.data.local.entidades.IntentoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface IntentoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun guardarIntento(intento: IntentoEntity)

    @Query("SELECT * FROM intentos WHERE sincronizado = 0")
    suspend fun buscarIntentosNoSincronizados(): List<IntentoEntity>

    @Query("SELECT * FROM intentos WHERE usuario_id = :usuarioId")
    fun buscarIntentosPorUsuarioId(usuarioId: String): Flow<List<IntentoEntity>>

    @Query("UPDATE intentos SET sincronizado = 1 WHERE id = :id")
    suspend fun marcarComoSincronizado(id: Int)
}