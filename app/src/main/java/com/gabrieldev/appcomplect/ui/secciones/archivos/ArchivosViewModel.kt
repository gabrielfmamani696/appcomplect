package com.gabrieldev.appcomplect.ui.secciones.archivos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabrieldev.appcomplect.data.repository.ArchivoRepository
import com.gabrieldev.appcomplect.data.repository.UsuarioRepository
import com.gabrieldev.appcomplect.model.Archivo
import com.gabrieldev.appcomplect.model.EspacioAprendizaje
import com.gabrieldev.appcomplect.model.EstadisticasArchivo
import com.gabrieldev.appcomplect.model.Usuario
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class ArchivosViewModel(
    private val archivoRepository: ArchivoRepository,
    private val usuarioRepository: UsuarioRepository
) : ViewModel() {

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje: StateFlow<String?> = _mensaje

    private val _archivos = MutableStateFlow<List<Archivo>>(emptyList())
    val archivos: StateFlow<List<Archivo>> = _archivos

    private val _archivosDivulgacion = MutableStateFlow<List<Archivo>>(emptyList())
    val archivosDivulgacion: StateFlow<List<Archivo>> = _archivosDivulgacion

    private val _archivosDescargados = MutableStateFlow<List<Archivo>>(emptyList())
    val archivosDescargados: StateFlow<List<Archivo>> = _archivosDescargados

    private val _misEspacios = MutableStateFlow<List<EspacioAprendizaje>>(emptyList())
    val misEspacios: StateFlow<List<EspacioAprendizaje>> = _misEspacios

    private val _estadisticasSeleccionadas = MutableStateFlow<EstadisticasArchivo?>(null)
    val estadisticasSeleccionadas: StateFlow<EstadisticasArchivo?> = _estadisticasSeleccionadas

    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando

    val usuarioActivo: StateFlow<Usuario?> = usuarioRepository.usuarioActivo.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    init {
        observeUsuario()
    }

    private fun observeUsuario() {
        viewModelScope.launch {
            usuarioActivo.collectLatest { usuario ->
                if (usuario != null) {
                    cargarEspacios(usuario.uuidSesion)
                    cargarArchivosDescargados()
                } else {
                    _misEspacios.value = emptyList()
                    _archivosDescargados.value = emptyList()
                }
            }
        }
    }

    fun cargarArchivos() {
        viewModelScope.launch {
            _cargando.value = true
            _archivos.value = archivoRepository.obtenerArchivosDocentesAdmin()
            _archivosDivulgacion.value = archivoRepository.obtenerArchivosEstudiantesDivulgacion()
            cargarArchivosDescargados()
            cargarEspacios(usuarioActivo.value?.uuidSesion)
            _cargando.value = false
        }
    }

    private fun cargarArchivosDescargados() {
        val idUsuario = usuarioActivo.value?.uuidSesion ?: return
        viewModelScope.launch(Dispatchers.IO) {
            val dbArchivos = archivoRepository.obtenerArchivosLocaleslUsuario(idUsuario)
            _archivosDescargados.value = dbArchivos
        }
    }

    private fun cargarEspacios(usuarioId: String?) {
        if (usuarioId.isNullOrBlank()) return
        viewModelScope.launch(Dispatchers.IO) {
            _misEspacios.value = archivoRepository.obtenerMisEspacios(UUID.fromString(usuarioId))
        }
    }

    fun cargarEstadisticas(idArchivo: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _estadisticasSeleccionadas.value = null
            _estadisticasSeleccionadas.value = archivoRepository.obtenerEstadisticasArchivo(UUID.fromString(idArchivo))
        }
    }

    fun eliminarArchivoDivulgacion(idArchivo: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val ok = archivoRepository.eliminarArchivoDivulgacionCompleto(idArchivo)
            if (ok) cargarArchivos()
            withContext(Dispatchers.Main) {
                onResult(ok)
            }
        }
    }

    fun crearEspacio(nombre: String, onResult: (String?) -> Unit) {
        val idUser = usuarioActivo.value?.uuidSesion ?: return
        viewModelScope.launch(Dispatchers.IO) {
            _cargando.value = true
            val codigo = archivoRepository.crearEspacioAprendizaje(UUID.fromString(idUser), nombre)
            if (codigo != null) cargarEspacios(idUser)
            _cargando.value = false
            withContext(Dispatchers.Main) {
                onResult(codigo)
            }
        }
    }

    fun unirseAEspacio(codigoAcceso: String, onResult: (Boolean, String) -> Unit) {
        val idUser = usuarioActivo.value?.uuidSesion ?: return
        viewModelScope.launch(Dispatchers.IO) {
            _cargando.value = true
            val espacio = archivoRepository.buscarEspacioPorCodigo(codigoAcceso.trim())
            if (espacio == null) {
                _cargando.value = false
                withContext(Dispatchers.Main) {
                    onResult(false, "Código inválido.")
                }
                return@launch
            }

            val ok = archivoRepository.unirseAEspacio(UUID.fromString(idUser), UUID.fromString(espacio.id))
            if (ok) cargarEspacios(idUser)
            _cargando.value = false
            withContext(Dispatchers.Main) {
                if (ok) {
                    onResult(true, "Te uniste al espacio ${espacio.nombre}.")
                } else {
                    onResult(false, "Error al unirse al espacio.")
                }
            }
        }
    }

    fun actualizarEspacioArchivo(archivoId: String, espacioId: String?, onResult: (Boolean) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            _cargando.value = true
            val ok = archivoRepository.actualizarEspacioArchivo(archivoId, espacioId)
            if (ok) cargarArchivos()
            _cargando.value = false
            withContext(Dispatchers.Main) {
                onResult(ok)
            }
        }
    }

    fun descargarArchivo(idArchivo: String, onResult: (Boolean) -> Unit) {
        val idUsuario = usuarioActivo.value?.uuidSesion ?: return
        viewModelScope.launch(Dispatchers.IO) {
            _cargando.value = true
            val descargaExitosaFlag = archivoRepository.descargarArchivoLocal(idUsuario, idArchivo)
            if (descargaExitosaFlag) cargarArchivosDescargados()
            _cargando.value = false
            withContext(Dispatchers.Main) {
                onResult(descargaExitosaFlag)
            }
        }
    }
}
