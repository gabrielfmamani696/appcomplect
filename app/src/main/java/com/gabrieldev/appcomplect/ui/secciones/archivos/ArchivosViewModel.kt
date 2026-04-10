package com.gabrieldev.appcomplect.ui.secciones.archivos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabrieldev.appcomplect.data.repository.ArchivoRepository
import com.gabrieldev.appcomplect.data.repository.UsuarioRepository
import com.gabrieldev.appcomplect.model.Archivo
import com.gabrieldev.appcomplect.model.Usuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ArchivosViewModel(
    private val archivoRepository: ArchivoRepository,
    usuarioRepository: UsuarioRepository
) : ViewModel() {

    private val _archivos = MutableStateFlow<List<Archivo>>(emptyList())
    val archivos: StateFlow<List<Archivo>> = _archivos

    private val _archivosDivulgacion = MutableStateFlow<List<Archivo>>(emptyList())
    val archivosDivulgacion: StateFlow<List<Archivo>> = _archivosDivulgacion

    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando

    val usuarioActivo: StateFlow<Usuario?> = usuarioRepository.usuarioActivo.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    fun cargarArchivos() {
        viewModelScope.launch {
            _cargando.value = true
            _archivos.value = archivoRepository.obtenerArchivosDocentesAdmin()
            _archivosDivulgacion.value = archivoRepository.obtenerArchivosEstudiantesDivulgacion()
            _cargando.value = false
        }
    }

    fun eliminarArchivoDivulgacion(idArchivo: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val ok = archivoRepository.eliminarArchivoDivulgacionCompleto(idArchivo)
            if (ok) cargarArchivos()
            onResult(ok)
        }
    }
}
