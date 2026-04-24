package com.gabrieldev.appcomplect.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gabrieldev.appcomplect.data.repository.ArchivoRepository
import com.gabrieldev.appcomplect.data.repository.AvatarRepository
import com.gabrieldev.appcomplect.data.repository.InsigniaRepository
import com.gabrieldev.appcomplect.data.repository.SyncRepository
import com.gabrieldev.appcomplect.data.repository.UsuarioRepository
import com.gabrieldev.appcomplect.ui.secciones.archivos.ArchivosViewModel
import com.gabrieldev.appcomplect.ui.secciones.perfilpersonal.PerfilViewModel

class AppViewModelFactory(
    private val usuarioRepository: UsuarioRepository,
    private val avatarRepository: AvatarRepository,
    private val archivoRepository: ArchivoRepository,
    private val insigniaRepository: InsigniaRepository,
    private val syncRepository: SyncRepository,
    private val context: Context? = null
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                if (context == null) throw IllegalArgumentException("Context requerido para MainViewModel")
                MainViewModel(usuarioRepository, insigniaRepository, syncRepository, context) as T
            }
            modelClass.isAssignableFrom(PerfilViewModel::class.java) -> {
                PerfilViewModel(usuarioRepository, avatarRepository) as T
            }
            modelClass.isAssignableFrom(RegistroViewModel::class.java) -> {
                RegistroViewModel(usuarioRepository, avatarRepository) as T
            }
            modelClass.isAssignableFrom(ArchivosViewModel::class.java) -> {
                ArchivosViewModel(archivoRepository, usuarioRepository) as T
            }
            else -> throw IllegalArgumentException("ViewModel desconocido: ${modelClass.name}")
        }
    }
}
