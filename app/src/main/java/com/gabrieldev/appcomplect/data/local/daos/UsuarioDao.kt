package com.gabrieldev.appcomplect.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.gabrieldev.appcomplect.data.local.entidades.UsuarioEntity

@Dao
interface UsuarioDao {

    @Upsert
    suspend fun guardarUsuario(usuario: UsuarioEntity)

    @Update
    suspend fun actualizarUsuario(usuario: UsuarioEntity)

    @Query("SELECT * FROM usuarios WHERE id = :id")
    suspend fun getUsuarioPorId(id: String): UsuarioEntity?

    @Query("SELECT * FROM usuarios WHERE sincronizado = 0")
    suspend fun getUsuariosNoSincronizados(): List<UsuarioEntity>

    @Query("UPDATE usuarios SET sincronizado = 1 WHERE id = :id")
    suspend fun marcarComoSincronizado(id: String)

    @Query("UPDATE usuarios SET sincronizado = 0 WHERE id = :id")
    suspend fun marcarComoNoSincronizado(id: String)

    @Query("SELECT EXISTS(SELECT 1 FROM usuarios WHERE id = :idUsuario)")
    suspend fun existeUsuario(idUsuario: String): Boolean
}