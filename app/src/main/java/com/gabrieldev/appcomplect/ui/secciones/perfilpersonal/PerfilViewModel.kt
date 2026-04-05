package com.gabrieldev.appcomplect.ui.secciones.perfilpersonal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabrieldev.appcomplect.data.repository.AvatarRepository
import com.gabrieldev.appcomplect.data.repository.UsuarioRepository
import com.gabrieldev.appcomplect.model.OpcionAvatar
import com.gabrieldev.appcomplect.model.RolUsuario
import com.gabrieldev.appcomplect.model.Usuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class PerfilUiState(
    val nombre: String = "",
    val apellidoPaterno: String = "",
    val apellidoMaterno: String = "",
    val alias: String = "",
    val numeroCelular: String = "",
    val curso: String = "",
    val paralelo: String = "",
    val nombreColegio: String = "",
    val horaH: String = "18",
    val horaM: String = "00",
    val avatarSeleccionado: OpcionAvatar? = null,
    val rolSeleccionado: RolUsuario? = null,
    val listaAvatares: List<OpcionAvatar> = emptyList(),
    val listaRoles: List<RolUsuario> = emptyList(),
    val expandedCurso: Boolean = false,
    val mensajeError: String? = null,
    val mensajeExito: String? = null,
    val guardando: Boolean = false
)

class PerfilViewModel(
    private val usuarioRepository: UsuarioRepository,
    private val avatarRepository: AvatarRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PerfilUiState())
    val uiState = _uiState.asStateFlow()

    private var currentUser: Usuario? = null

    init {
        cargarDatosIniciales()
    }

    private fun cargarDatosIniciales() {
        viewModelScope.launch {
            val avatares = avatarRepository.obtenerAvatares()
            val roles = usuarioRepository.obtenerRoles().filter { !it.nombreRol.contains("admin", true) }
            _uiState.value = _uiState.value.copy(
                listaAvatares = avatares,
                listaRoles = roles
            )
            currentUser?.let { sincronizarEstado(it) }
        }
    }

    fun initUsuario(usuario: Usuario) {
        if (currentUser == usuario) return
        currentUser = usuario
        sincronizarEstado(usuario)
    }

    private fun sincronizarEstado(usuario: Usuario) {
        val horaParts = usuario.horaNotificacion?.split(":") ?: listOf("18", "00")
        val avatarOpt = _uiState.value.listaAvatares.find { it.idAvatar == usuario.idAvatar }
        val rolOpt = _uiState.value.listaRoles.find { it.id == usuario.idRol }

        _uiState.value = _uiState.value.copy(
            nombre = usuario.nombre,
            apellidoPaterno = usuario.apellidoPaterno,
            apellidoMaterno = usuario.apellidoMaterno,
            alias = usuario.alias,
            numeroCelular = usuario.numeroCelular,
            curso = usuario.curso ?: "",
            paralelo = usuario.paralelo ?: "",
            nombreColegio = usuario.nombreColegio ?: "",
            horaH = horaParts.getOrNull(0) ?: "18",
            horaM = horaParts.getOrNull(1) ?: "00",
            avatarSeleccionado = avatarOpt,
            rolSeleccionado = rolOpt,
            mensajeError = null,
            mensajeExito = null
        )
    }

    fun actualizarCampo(campo: String, valor: String) {
        _uiState.value = when (campo) {
            "nombre" -> _uiState.value.copy(nombre = valor)
            "apellidoPaterno" -> _uiState.value.copy(apellidoPaterno = valor)
            "apellidoMaterno" -> _uiState.value.copy(apellidoMaterno = valor)
            "alias" -> _uiState.value.copy(alias = valor)
            "numeroCelular" -> _uiState.value.copy(numeroCelular = valor)
            "curso" -> _uiState.value.copy(curso = valor)
            "paralelo" -> _uiState.value.copy(paralelo = valor)
            "nombreColegio" -> _uiState.value.copy(nombreColegio = valor)
            "horaH" -> _uiState.value.copy(horaH = valor)
            "horaM" -> _uiState.value.copy(horaM = valor)
            else -> _uiState.value
        }
    }

    fun seleccionarAvatar(avatar: OpcionAvatar) {
        _uiState.value = _uiState.value.copy(avatarSeleccionado = avatar)
    }

    fun seleccionarRol(rol: RolUsuario) {
        _uiState.value = _uiState.value.copy(rolSeleccionado = rol)
    }

    fun toggleExpandedCurso() {
        _uiState.value = _uiState.value.copy(expandedCurso = !_uiState.value.expandedCurso)
    }

    fun cerrarSesion() {
        viewModelScope.launch {
            usuarioRepository.cerrarSesion()
        }
    }

    fun guardarCambios() {
        val state = _uiState.value
        if (state.nombre.length < 4) {
            _uiState.value = state.copy(mensajeError = "El nombre es muy corto", mensajeExito = null)
            return
        }
        if (state.numeroCelular.length < 8) {
            _uiState.value = state.copy(mensajeError = "Ingresa un número de celular válido", mensajeExito = null)
            return
        }
        if (state.rolSeleccionado == null) {
            _uiState.value = state.copy(mensajeError = "Selecciona si eres Estudiante o Docente", mensajeExito = null)
            return
        }
        if (state.alias.length < 4) {
            _uiState.value = state.copy(mensajeError = "El alias debe tener al menos 4 letras", mensajeExito = null)
            return
        }

        val h = state.horaH.toIntOrNull() ?: 18
        val m = state.horaM.toIntOrNull() ?: 0
        val horaLimpia = "${h.toString().padStart(2, '0')}:${m.toString().padStart(2, '0')}"

        _uiState.value = state.copy(guardando = true, mensajeError = null, mensajeExito = null)

        viewModelScope.launch {
            val userBase = currentUser ?: return@launch
            val upd = userBase.copy(
                nombre = state.nombre,
                apellidoPaterno = state.apellidoPaterno,
                apellidoMaterno = state.apellidoMaterno,
                alias = state.alias,
                idAvatar = state.avatarSeleccionado?.idAvatar ?: userBase.idAvatar,
                avatarUrl = state.avatarSeleccionado?.urlImagen ?: userBase.avatarUrl,
                numeroCelular = state.numeroCelular,
                curso = state.curso.takeIf { it.isNotBlank() },
                paralelo = state.paralelo.takeIf { it.isNotBlank() },
                nombreColegio = state.nombreColegio.takeIf { it.isNotBlank() },
                idRol = state.rolSeleccionado.id,
                nombreRol = state.rolSeleccionado.nombreRol,
                horaNotificacion = horaLimpia
            )
            
            val exito = usuarioRepository.actualizarUsuarioPerfil(upd)
            if (exito) {
                _uiState.value = _uiState.value.copy(guardando = false, mensajeExito = "Perfil actualizado correctamente.", mensajeError = null)
            } else {
                _uiState.value = _uiState.value.copy(guardando = false, mensajeError = "Hubo un error al actualizar.", mensajeExito = null)
            }
        }
    }
}
