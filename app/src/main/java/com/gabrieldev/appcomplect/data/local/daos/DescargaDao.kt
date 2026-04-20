package com.gabrieldev.appcomplect.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Upsert
import com.gabrieldev.appcomplect.data.local.entidades.DescargaEntity

@Dao
interface DescargaDao {

    @Upsert
    suspend fun guardarDescarga(descarga: DescargaEntity)

}