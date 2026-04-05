package com.gabrieldev.appcomplect.ui

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

data class RegistroUiState(
    val esModoLogin: Boolean = false,
    val nombre: String = "",
    val apellidoPaterno: String = "",
    val apellidoMaterno: String = "",
    val alias: String = "",
    val numeroCelular: String = "",
    val curso: String = "",
    val paralelo: String = "",
    val nombreColegio: String = "",
    val avatarSeleccionado: OpcionAvatar? = null,
    val rolSeleccionado: RolUsuario? = null,
    val listaAvatares: List<OpcionAvatar> = emptyList(),
    val listaRoles: List<RolUsuario> = emptyList(),
    val expandedCurso: Boolean = false,
    val mensajeError: String? = null,
    val cargando: Boolean = false
)

class RegistroViewModel(
    private val usuarioRepository: UsuarioRepository,
    private val avatarRepository: AvatarRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegistroUiState())
    val uiState = _uiState.asStateFlow()

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
        }
    }

    fun toggleModoLogin() {
        _uiState.value = _uiState.value.copy(
            esModoLogin = !_uiState.value.esModoLogin,
            mensajeError = null
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

    fun intentarRegistroOLogin(alTerminar: () -> Unit) {
        val state = _uiState.value
        if (state.esModoLogin) {
            if (state.numeroCelular.length < 8 || state.nombre.isEmpty() || state.apellidoPaterno.isEmpty()) {
                _uiState.value = state.copy(mensajeError = "Ingresa tu número de celular, nombre y apellido para iniciar sesión")
                return
            }
            
            _uiState.value = state.copy(cargando = true, mensajeError = null)
            viewModelScope.launch {
                val exito = usuarioRepository.loginUsuario(state.numeroCelular, state.nombre, state.apellidoPaterno)
                _uiState.value = _uiState.value.copy(cargando = false)
                if (exito) {
                    limpiarCampos()
                    alTerminar()
                } else {
                    _uiState.value = _uiState.value.copy(mensajeError = "No se encontró un usuario con ese número o hubo un error")
                }
            }
        } else {
            if (state.nombre.length < 4 || state.apellidoPaterno.isEmpty()) {
                _uiState.value = state.copy(mensajeError = "Debes proveer al menos tu nombre (min 4 letras) y apellido paterno")
                return
            }
            if (state.numeroCelular.length < 8) {
                _uiState.value = state.copy(mensajeError = "Ingresa un número de celular válido")
                return
            }
            if (state.rolSeleccionado == null) {
                _uiState.value = state.copy(mensajeError = "Selecciona si eres Estudiante o Docente")
                return
            }
            if (state.alias.length < 4) {
                _uiState.value = state.copy(mensajeError = "El alias debe tener al menos 4 letras")
                return
            }
            
            _uiState.value = state.copy(cargando = true, mensajeError = null)
            viewModelScope.launch {
                val user = Usuario(
                    alias = state.alias,
                    idRol = state.rolSeleccionado.id,
                    numeroCelular = state.numeroCelular,
                    curso = state.curso.takeIf { it.isNotBlank() },
                    paralelo = state.paralelo.takeIf { it.isNotBlank() },
                    nombreColegio = state.nombreColegio.takeIf { it.isNotBlank() },
                    nombre = state.nombre,
                    apellidoPaterno = state.apellidoPaterno,
                    apellidoMaterno = state.apellidoMaterno,
                    idAvatar = state.avatarSeleccionado?.idAvatar ?: "",
                    avatarUrl = state.avatarSeleccionado?.urlImagen ?: "",
                    estrellasPrestigio = 0,
                    rachaActualDias = 0,
                    nombreRol = state.rolSeleccionado.nombreRol
                )
                
                val exito = try {
                    usuarioRepository.registrarUsuario(user)
                    true
                } catch (e: Exception) {
                    false
                }
                
                _uiState.value = _uiState.value.copy(cargando = false)
                if (exito) {
                    limpiarCampos()
                    alTerminar()
                } else {
                    _uiState.value = _uiState.value.copy(mensajeError = "Hubo un problema al registrar al usuario. Verifica tu conexión o intenta con otro número.")
                }
            }
        }
    }

    private fun limpiarCampos() {
        // Mantener lista de avatares y roles, pero borrar el input del usuario
        _uiState.value = _uiState.value.copy(
            nombre = "",
            apellidoPaterno = "",
            apellidoMaterno = "",
            alias = "",
            numeroCelular = "",
            curso = "",
            paralelo = "",
            nombreColegio = "",
            avatarSeleccionado = null,
            rolSeleccionado = null,
            mensajeError = null
        )
    }
}
