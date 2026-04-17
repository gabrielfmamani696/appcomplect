package com.gabrieldev.appcomplect.ui.secciones.archivos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.gabrieldev.appcomplect.data.repository.ArchivoRepository
import com.gabrieldev.appcomplect.data.repository.UsuarioRepository
import com.gabrieldev.appcomplect.model.BorradorPreguntaDivulgacion
import com.gabrieldev.appcomplect.model.BorradorRespuestaDivulgacion
import com.gabrieldev.appcomplect.model.BorradorTarjetaDivulgacion
import com.gabrieldev.appcomplect.model.Nivel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

data class CuestionarioBorradorDivulgacion(
    val idTemporal: String,
    val titulo: String,
    val preguntas: List<PreguntaBorradorDivulgacion>
)

data class PreguntaBorradorDivulgacion(
    val enunciado: String,
    val respuestas: List<BorradorRespuestaDivulgacion>
)

class CrearArchivoViewModel(
    private val archivoRepository: ArchivoRepository,
    private val usuarioRepository: UsuarioRepository,
    val idArchivoEditar: String?
) : ViewModel() {

    private val _titulo = MutableStateFlow("")
    val titulo: StateFlow<String> = _titulo.asStateFlow()

    private val _tema = MutableStateFlow("")
    val tema: StateFlow<String> = _tema.asStateFlow()

    private val _descripcion = MutableStateFlow("")
    val descripcion: StateFlow<String> = _descripcion.asStateFlow()

    private val _autorOriginal = MutableStateFlow<String?>(null)
    val autorOriginal: StateFlow<String?> = _autorOriginal.asStateFlow()

    private val _licencia = MutableStateFlow<String?>(null)
    val licencia: StateFlow<String?> = _licencia.asStateFlow()

    private val _niveles = MutableStateFlow<List<Nivel>>(emptyList())
    val niveles: StateFlow<List<Nivel>> = _niveles.asStateFlow()

    private val _nivelSeleccionadoId = MutableStateFlow<String?>(null)
    val nivelSeleccionadoId: StateFlow<String?> = _nivelSeleccionadoId.asStateFlow()

    private val _imagenPortada = MutableStateFlow<String?>(null)
    val imagenPortada: StateFlow<String?> = _imagenPortada.asStateFlow()

    private val _tarjetas = MutableStateFlow<List<BorradorTarjetaDivulgacion>>(emptyList())
    val tarjetas: StateFlow<List<BorradorTarjetaDivulgacion>> = _tarjetas.asStateFlow()

    private val _listaCuestionarios = MutableStateFlow<List<CuestionarioBorradorDivulgacion>>(emptyList())
    val listaCuestionarios: StateFlow<List<CuestionarioBorradorDivulgacion>> = _listaCuestionarios.asStateFlow()

    private val _cuestionarioActivoId = MutableStateFlow<String?>(null)
    val cuestionarioActivoId: StateFlow<String?> = _cuestionarioActivoId.asStateFlow()

    private val _quiereCuestionario = MutableStateFlow<Boolean?>(null)
    val quiereCuestionario: StateFlow<Boolean?> = _quiereCuestionario.asStateFlow()

    private val _mensajeUsuario = MutableStateFlow<String?>(null)
    val mensajeUsuario: StateFlow<String?> = _mensajeUsuario.asStateFlow()

    private val _navegarAtras = MutableStateFlow(false)
    val navegarAtras: StateFlow<Boolean> = _navegarAtras.asStateFlow()

    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando.asStateFlow()

    private val nivelSeleccionado: Nivel?
        get() = _niveles.value.find { it.id == _nivelSeleccionadoId.value }

    private val maxPalabrasPorTarjeta: Int
        get() = nivelSeleccionado?.limitePalabrasTarjeta
            ?: usuarioRepository.usuarioActivo.value?.nivel?.limitePalabrasTarjeta
            ?: 50

    val esEstudiante: Boolean
        get() = usuarioRepository.usuarioActivo.value?.nombreRol?.contains("estudiante", true) ?: false

    val esDocente: Boolean
        get() = usuarioRepository.usuarioActivo.value?.nombreRol?.lowercase()?.contains("docente") == true ||
                usuarioRepository.usuarioActivo.value?.nombreRol?.lowercase()?.contains("admin") == true

    init {
        viewModelScope.launch {
            _niveles.value = usuarioRepository.obtenerTodosLosNiveles().sortedBy { it.jerarquia }
        }
        if (!idArchivoEditar.isNullOrBlank()) {
            cargarParaEdicion(idArchivoEditar)
        }
    }

    fun cargarParaEdicion(id: String) {
        viewModelScope.launch {
            _cargando.value = true
            val meta = archivoRepository.obtenerArchivoParaEdicion(id)
            val uid = usuarioRepository.usuarioActivo.value?.uuidSesion
            if (meta == null || uid == null || meta.idAutor != uid) {
                _mensajeUsuario.value = "No se pudo cargar o no tienes permiso."
                _cargando.value = false
                return@launch
            }
            _titulo.value = meta.titulo
            _tema.value = meta.tema
            _descripcion.value = meta.descripcion
            _imagenPortada.value = meta.urlPortada
            _autorOriginal.value = meta.autorOriginal
            _licencia.value = meta.licencia
            _nivelSeleccionadoId.value = meta.nivelRequeridoId ?: usuarioRepository.usuarioActivo.value?.nivel?.id
            val contenido = archivoRepository.obtenerContenidoArchivo(id)
            if (contenido != null) {
                _tarjetas.value = contenido.tarjetas.map { t ->
                    BorradorTarjetaDivulgacion(t.contenidoTexto, t.tipoFondo, t.dataFondo)
                }
                val c = contenido.cuestionario
                if (c != null && c.preguntas.isNotEmpty()) {
                    _quiereCuestionario.value = true
                    val preg = c.preguntas.map { p ->
                        PreguntaBorradorDivulgacion(
                            enunciado = p.enunciado,
                            respuestas = p.respuestas.map { r ->
                                BorradorRespuestaDivulgacion(r.textoOpcion, r.esCorrecta)
                            }
                        )
                    }
                    _listaCuestionarios.value = listOf(
                        CuestionarioBorradorDivulgacion(
                            idTemporal = UUID.randomUUID().toString(),
                            titulo = c.tituloQuiz,
                            preguntas = preg
                        )
                    )
                } else {
                    _quiereCuestionario.value = false
                }
            }
            _cargando.value = false
        }
    }

    fun actualizarTitulo(v: String) { _titulo.value = v }
    fun actualizarTema(v: String) { _tema.value = v }
    fun actualizarDescripcion(v: String) { _descripcion.value = v }
    fun actualizarAutorOriginal(v: String) { _autorOriginal.value = v }
    fun actualizarLicencia(v: String) { _licencia.value = v }
    fun seleccionarNivel(id: String?) { _nivelSeleccionadoId.value = id }
    fun actualizarImagenPortada(v: String?) { _imagenPortada.value = v }
    fun setQuiereCuestionario(v: Boolean) { 
        _quiereCuestionario.value = v 
        if (!v) _listaCuestionarios.value = emptyList()
    }

    fun agregarTarjeta(contenido: String, tipoFondo: String, dataFondo: String) {
        if (contenido.isBlank()) return
        if (esDocente && _nivelSeleccionadoId.value.isNullOrBlank()) {
            _mensajeUsuario.value = "Selecciona un nivel antes de añadir tarjetas."
            return
        }
        val palabras = contenido.trim().split("\\s+".toRegex()).filter { it.isNotBlank() }.size
        if (palabras > maxPalabrasPorTarjeta) {
            _mensajeUsuario.value = "Cada tarjeta admite máximo $maxPalabrasPorTarjeta palabras."
            return
        }
        _tarjetas.value = _tarjetas.value + BorradorTarjetaDivulgacion(contenido, tipoFondo, dataFondo)
    }

    fun eliminarTarjeta(index: Int) {
        val list = _tarjetas.value.toMutableList()
        if (index in list.indices) {
            list.removeAt(index)
            _tarjetas.value = list
        }
    }

    fun crearNuevoCuestionario(title: String) {
        if (title.isBlank()) return
        if (_listaCuestionarios.value.isNotEmpty()) {
            _mensajeUsuario.value = "Solo un cuestionario por publicación."
            return
        }
        val nuevo = CuestionarioBorradorDivulgacion(
            idTemporal = UUID.randomUUID().toString(),
            titulo = title,
            preguntas = emptyList()
        )
        _listaCuestionarios.value = _listaCuestionarios.value + nuevo
    }

    fun seleccionarCuestionario(id: String?) { _cuestionarioActivoId.value = id }

    fun eliminarCuestionario(id: String) {
        _listaCuestionarios.value = _listaCuestionarios.value.filter { it.idTemporal != id }
        if (_cuestionarioActivoId.value == id) _cuestionarioActivoId.value = null
    }

    fun actualizarTituloCuestionario(titulo: String) {
        val id = _cuestionarioActivoId.value ?: return
        _listaCuestionarios.value = _listaCuestionarios.value.map {
            if (it.idTemporal == id) it.copy(titulo = titulo) else it
        }
    }

    fun agregarPregunta(enunciado: String, respuestas: List<BorradorRespuestaDivulgacion>) {
        val id = _cuestionarioActivoId.value ?: return
        val pq = PreguntaBorradorDivulgacion(enunciado, respuestas)
        _listaCuestionarios.value = _listaCuestionarios.value.map {
            if (it.idTemporal == id) it.copy(preguntas = it.preguntas + pq) else it
        }
    }

    fun eliminarPregunta(index: Int) {
        val id = _cuestionarioActivoId.value ?: return
        _listaCuestionarios.value = _listaCuestionarios.value.map {
            if (it.idTemporal == id) {
                val list = it.preguntas.toMutableList()
                if (index in list.indices) list.removeAt(index)
                it.copy(preguntas = list)
            } else it
        }
    }

    fun actualizarMensaje(m: String) { _mensajeUsuario.value = m }

    fun limpiarMensajes() { _mensajeUsuario.value = null }

    fun guardarTodo() {
        viewModelScope.launch {
            val uidStr = usuarioRepository.usuarioActivo.value?.uuidSesion
            if (uidStr.isNullOrBlank()) {
                _mensajeUsuario.value = "Sesión no válida."
                return@launch
            }
            if (_titulo.value.length < 4) {
                _mensajeUsuario.value = "El título debe tener al menos 4 caracteres."
                return@launch
            }
            if (_tema.value.length < 4) {
                _mensajeUsuario.value = "El tema debe tener al menos 4 caracteres."
                return@launch
            }
            if (_descripcion.value.length < 4) {
                _mensajeUsuario.value = "La descripción debe tener al menos 4 caracteres."
                return@launch
            }
            if (_tarjetas.value.isEmpty()) {
                _mensajeUsuario.value = "Agrega al menos una tarjeta."
                return@launch
            }

            _cargando.value = true
            
            var finalPortada = _imagenPortada.value
            if (finalPortada != null && (finalPortada.startsWith("content://") || finalPortada.startsWith("file://"))) {
                _mensajeUsuario.value = "Subiendo portada..."
                finalPortada = archivoRepository.subirImagen(finalPortada, "portadas")
                if (finalPortada == null) {
                    _mensajeUsuario.value = "Error al subir la imagen de portada."
                    _cargando.value = false
                    return@launch
                }
                _imagenPortada.value = finalPortada
            }

            val finalTarjetas = _tarjetas.value.toMutableList()
            for (i in finalTarjetas.indices) {
                val t = finalTarjetas[i]
                if (t.tipoFondo == "imagen" && (t.dataFondo.startsWith("content://") || t.dataFondo.startsWith("file://"))) {
                    _mensajeUsuario.value = "Subiendo imagen de tarjeta ${i + 1}..."
                    val url = archivoRepository.subirImagen(t.dataFondo, "tarjetas")
                    if (url == null) {
                        _mensajeUsuario.value = "Error al subir imagen de tarjeta ${i + 1}."
                        _cargando.value = false
                        return@launch
                    }
                    finalTarjetas[i] = t.copy(dataFondo = url)
                }
            }
            _tarjetas.value = finalTarjetas

            if (esEstudiante) {
                val cuestionario = _listaCuestionarios.value.firstOrNull()
                if (cuestionario != null) {
                    if (cuestionario.preguntas.size < 3) {
                        _mensajeUsuario.value = "El cuestionario debe tener al menos 3 preguntas."
                        _cargando.value = false
                        return@launch
                    }
                    
                    for (p in cuestionario.preguntas) {
                        if (p.respuestas.any { it.textoOpcion.isBlank() }) {
                            _mensajeUsuario.value = "Todas las opciones de respuesta deben estar llenas."
                            _cargando.value = false
                            return@launch
                        }
                    }
                }
            }

            val preguntasModelo = _listaCuestionarios.value.firstOrNull()?.preguntas?.map { p ->
                BorradorPreguntaDivulgacion(p.enunciado, p.respuestas)
            } ?: emptyList()
            val tituloQuiz = _listaCuestionarios.value.firstOrNull()?.titulo
            val usuarioId = UUID.fromString(uidStr)
            val usuarioActual = usuarioRepository.usuarioActivo.value

            if (esDocente && _nivelSeleccionadoId.value.isNullOrBlank()) {
                _mensajeUsuario.value = "Debes seleccionar un nivel para esta publicación."
                _cargando.value = false
                return@launch
            }

            val nivelId = _nivelSeleccionadoId.value?.let { UUID.fromString(it) }
                ?: usuarioActual?.nivel?.id?.let { UUID.fromString(it) }
                ?: archivoRepository.primerNivelId()

            val autorOriginalFinal = _autorOriginal.value?.trim()?.takeIf { it.isNotEmpty() }
            val licenciaFinal = _licencia.value?.trim()?.takeIf { it.isNotEmpty() }

            val ok = if (idArchivoEditar.isNullOrBlank()) {
                archivoRepository.crearArchivoDivulgacionCompleto(
                    titulo = _titulo.value,
                    tema = _tema.value.ifBlank { "General" },
                    descripcion = _descripcion.value.ifBlank { " " },
                    imagenPortada = _imagenPortada.value,
                    usuarioId = usuarioId,
                    nivelRequeridoUuid = nivelId,
                    autorOriginal = autorOriginalFinal,
                    licencia = licenciaFinal,
                    tarjetas = _tarjetas.value,
                    tituloQuiz = tituloQuiz,
                    preguntas = preguntasModelo
                ) != null
            } else {
                val meta = archivoRepository.obtenerArchivoParaEdicion(idArchivoEditar)
                if (meta?.idAutor != uidStr) {
                    _cargando.value = false
                    _mensajeUsuario.value = "No autorizado."
                    return@launch
                }
                archivoRepository.actualizarArchivoDivulgacionCompleto(
                    idArchivo = idArchivoEditar,
                    titulo = _titulo.value,
                    tema = _tema.value.ifBlank { "General" },
                    descripcion = _descripcion.value.ifBlank { " " },
                    imagenPortada = _imagenPortada.value,
                    nivelRequeridoUuid = nivelId,
                    autorOriginal = autorOriginalFinal,
                    licencia = licenciaFinal,
                    tarjetas = _tarjetas.value,
                    tituloQuiz = tituloQuiz,
                    preguntas = preguntasModelo
                )
            }
            _cargando.value = false
            if (ok) {
                runCatching { usuarioRepository.registrarAccionDiaria() }
                _navegarAtras.value = true
            } else {
                _mensajeUsuario.value = "No se pudo guardar. Intenta de nuevo."
            }
        }
    }
}

class CrearArchivoViewModelFactory(
    private val archivoRepository: ArchivoRepository,
    private val usuarioRepository: UsuarioRepository,
    private val idArchivoEditar: String?
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CrearArchivoViewModel(archivoRepository, usuarioRepository, idArchivoEditar) as T
    }
}
