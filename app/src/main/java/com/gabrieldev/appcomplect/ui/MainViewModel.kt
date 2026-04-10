package com.gabrieldev.appcomplect.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabrieldev.appcomplect.data.repository.UsuarioRepository
import com.gabrieldev.appcomplect.data.repository.InsigniaRepository
import com.gabrieldev.appcomplect.data.repository.ContextoInsignia
import com.gabrieldev.appcomplect.model.InsigniaObtenida
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val usuarioRepository: UsuarioRepository,
    private val insigniaRepository: InsigniaRepository
) : ViewModel() {

    val usuarioActivo = usuarioRepository.usuarioActivo
    val estaCargando = usuarioRepository.cargando

    private val _insigniasNuevas = MutableStateFlow<List<InsigniaObtenida>>(emptyList())
    val insigniasNuevas = _insigniasNuevas.asStateFlow()

    init {
        verificarSesion()
    }

    private fun verificarSesion() {
        viewModelScope.launch {
            usuarioRepository.verificarSesion()
        }
    }

    fun registrarAccionDiaria() {
        viewModelScope.launch {
            usuarioRepository.registrarAccionDiaria()
            val usuarioConRacha = usuarioRepository.usuarioActivo.value
            val uid = usuarioConRacha?.uuidSesion
            if (!uid.isNullOrBlank()) {
                val nuevas = insigniaRepository.verificarInsignias(
                    usuarioId = uid,
                    contexto = ContextoInsignia.Racha(
                        rachaActualDias = usuarioConRacha.rachaActualDias
                    )
                )
                if (nuevas.isNotEmpty()) _insigniasNuevas.value = nuevas
            }
        }
    }

    fun limpiarInsignias() {
        _insigniasNuevas.value = emptyList()
    }
}
