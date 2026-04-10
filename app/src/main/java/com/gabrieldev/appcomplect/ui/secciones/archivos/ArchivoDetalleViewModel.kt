package com.gabrieldev.appcomplect.ui.secciones.archivos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.gabrieldev.appcomplect.data.repository.ArchivoRepository
import com.gabrieldev.appcomplect.data.repository.ContextoInsignia
import com.gabrieldev.appcomplect.data.repository.InsigniaRepository
import com.gabrieldev.appcomplect.data.repository.UsuarioRepository
import com.gabrieldev.appcomplect.model.ContenidoArchivo
import com.gabrieldev.appcomplect.model.InsigniaObtenida
import com.gabrieldev.appcomplect.model.Usuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID

sealed class EstadoCuestionario {
    object Cargando : EstadoCuestionario()
    object SinCuestionario : EstadoCuestionario()
    data class Error(val mensaje: String) : EstadoCuestionario()
    data class CuestionarioActivo(val datos: ContenidoArchivo) : EstadoCuestionario()
    data class Finalizado(val nota: Int, val aprobado: Boolean, val estrellasGanadas: Int) : EstadoCuestionario()
}

class ArchivoDetalleViewModel(
    private val archivoRepository: ArchivoRepository,
    private val usuarioRepository: UsuarioRepository,
    private val insigniaRepository: InsigniaRepository,
    val idArchivo: String
) : ViewModel() {

    private val _estado = MutableStateFlow<EstadoCuestionario>(EstadoCuestionario.Cargando)
    val estado: StateFlow<EstadoCuestionario> = _estado

    private val _contenido = MutableStateFlow<ContenidoArchivo?>(null)
    val contenido: StateFlow<ContenidoArchivo?> = _contenido

    private val _indicePreguntaActual = MutableStateFlow(0)
    val indicePreguntaActual: StateFlow<Int> = _indicePreguntaActual

    private val _respuestasUsuario = MutableStateFlow<Map<UUID, UUID>>(emptyMap())
    val respuestasUsuario: StateFlow<Map<UUID, UUID>> = _respuestasUsuario

    private val _mostrando = MutableStateFlow(false)
    val mostrando: StateFlow<Boolean> = _mostrando

    private val _insigniasNuevas = MutableStateFlow<List<InsigniaObtenida>>(emptyList())
    val insigniasNuevas: StateFlow<List<InsigniaObtenida>> = _insigniasNuevas

    private var articuloCompletadoDisparado = false

    val usuarioActivo: StateFlow<Usuario?> = usuarioRepository.usuarioActivo.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    init {
        cargar()
    }

    private fun cargar() {
        viewModelScope.launch {
            _estado.value = EstadoCuestionario.Cargando
            try {
                val resultado = archivoRepository.obtenerContenidoArchivo(idArchivo)
                if (resultado == null) {
                    _estado.value = EstadoCuestionario.Error("No se encontró el contenido del archivo o el ID es inválido ($idArchivo).")
                    return@launch
                }
                _contenido.value = resultado
                if (resultado.cuestionario == null || resultado.cuestionario.preguntas.isEmpty()) {
                    _estado.value = EstadoCuestionario.SinCuestionario
                } else {
                    _estado.value = EstadoCuestionario.CuestionarioActivo(resultado)
                }
            } catch (e: Exception) {
                _estado.value = EstadoCuestionario.Error("Error inesperado: ${e.localizedMessage ?: e.message}")
            }
        }
    }

    fun iniciarCuestionario(usuarioId: String?) {
        _mostrando.value = true
        _indicePreguntaActual.value = 0
        _respuestasUsuario.value = emptyMap()

        val usuarioFinal = usuarioId ?: usuarioActivo.value?.uuidSesion
        if (articuloCompletadoDisparado || usuarioFinal.isNullOrBlank()) return

        viewModelScope.launch {
            val nuevas = insigniaRepository.verificarInsignias(
                usuarioId = usuarioFinal,
                contexto = ContextoInsignia.ArticuloCompletado
            )
            if (nuevas.isNotEmpty()) _insigniasNuevas.value = nuevas
            articuloCompletadoDisparado = true
        }
    }

    fun seleccionarRespuesta(idPregunta: UUID, idRespuesta: UUID) {
        _respuestasUsuario.value = _respuestasUsuario.value + (idPregunta to idRespuesta)
    }

    fun siguientePregunta() {
        val cuestionario = (_estado.value as? EstadoCuestionario.CuestionarioActivo)?.datos?.cuestionario ?: return
        if (_indicePreguntaActual.value < cuestionario.preguntas.size - 1) {
            _indicePreguntaActual.value++
        }
    }

    fun anteriorPregunta() {
        if (_indicePreguntaActual.value > 0) {
            _indicePreguntaActual.value--
        }
    }

    fun finalizarCuestionario(usuarioId: String) {
        val usuarioFinal = usuarioId.ifBlank { usuarioActivo.value?.uuidSesion ?: "" }
        if (usuarioFinal.isBlank()) {
            _estado.value = EstadoCuestionario.Error("No se pudo identificar al usuario activo.")
            return
        }
        val cuestionario = (_estado.value as? EstadoCuestionario.CuestionarioActivo)?.datos?.cuestionario ?: return
        val respuestas = _respuestasUsuario.value
        var correctas = 0
        cuestionario.preguntas.forEach { pregunta ->
            val seleccionada = respuestas[pregunta.id]
            val esCorrecta = pregunta.respuestas.any { r -> r.id == seleccionada && r.esCorrecta }
            if (esCorrecta) correctas++
        }
        val total = cuestionario.preguntas.size
        val nota = if (total > 0) (correctas * 100) / total else 0
        val aprobado = nota >= 60
        val estrellasGanadas = correctas

        viewModelScope.launch {
            usuarioRepository.registrarResultadoExamen(
                usuarioId = usuarioFinal,
                archivoId = idArchivo,
                calificacion = nota,
                estrellasGanadas = estrellasGanadas
            )
            _estado.value = EstadoCuestionario.Finalizado(nota, aprobado, estrellasGanadas)

            val nuevas = insigniaRepository.verificarInsignias(
                usuarioId = usuarioFinal,
                contexto = ContextoInsignia.Examen(calificacion = nota)
            )
            if (nuevas.isNotEmpty()) _insigniasNuevas.value = nuevas
        }
    }

    fun limpiarInsignias() {
        _insigniasNuevas.value = emptyList()
    }

    fun reiniciarCuestionario(onVolverAArchivos: (() -> Unit)? = null) {
        val contenidoActual = _contenido.value
        if (contenidoActual == null || onVolverAArchivos != null) {
            onVolverAArchivos?.invoke()
            return
        }
        _indicePreguntaActual.value = 0
        _respuestasUsuario.value = emptyMap()
        _mostrando.value = false
        if (contenidoActual.cuestionario != null && contenidoActual.cuestionario.preguntas.isNotEmpty()) {
            _estado.value = EstadoCuestionario.CuestionarioActivo(contenidoActual)
        }
    }
}

class ArchivoDetalleViewModelFactory(
    private val archivoRepository: ArchivoRepository,
    private val usuarioRepository: UsuarioRepository,
    private val insigniaRepository: InsigniaRepository,
    private val idArchivo: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return ArchivoDetalleViewModel(
            archivoRepository, usuarioRepository, insigniaRepository, idArchivo
        ) as T
    }
}
