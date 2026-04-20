package com.gabrieldev.appcomplect.data.local;

import android.content.Context
import androidx.room.Database;
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gabrieldev.appcomplect.data.local.daos.ArchivoDao;
import com.gabrieldev.appcomplect.data.local.daos.DescargaDao
import com.gabrieldev.appcomplect.data.local.daos.IntentoDao;
import com.gabrieldev.appcomplect.data.local.daos.UsuarioDao;
import com.gabrieldev.appcomplect.data.local.entidades.ArchivoEntity;
import com.gabrieldev.appcomplect.data.local.entidades.CuestionarioEntity;
import com.gabrieldev.appcomplect.data.local.entidades.DescargaEntity;
import com.gabrieldev.appcomplect.data.local.entidades.IntentoEntity;
import com.gabrieldev.appcomplect.data.local.entidades.PreguntaEntity;
import com.gabrieldev.appcomplect.data.local.entidades.RespuestaEntity;
import com.gabrieldev.appcomplect.data.local.entidades.TarjetaEntity;
import com.gabrieldev.appcomplect.data.local.entidades.UsuarioEntity;

@Database(
    entities = [
        UsuarioEntity::class,
        ArchivoEntity::class,
        TarjetaEntity::class,
        CuestionarioEntity::class,
        PreguntaEntity::class,
        RespuestaEntity::class,
        IntentoEntity::class,
        DescargaEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppComplectDatabase: RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao
    abstract fun archivoDao(): ArchivoDao
    abstract fun intentoDao(): IntentoDao
    abstract fun descargaDao(): DescargaDao

    companion object {
        @Volatile
        private var instance: AppComplectDatabase? = null

        fun getInstance(context: Context): AppComplectDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppComplectDatabase::class.java,
                    "appcomplect_db"
                )
                .fallbackToDestructiveMigration(true)
                .build().also { instance = it }
            }
        }
    }
}
