package com.gabrieldev.appcomplect.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabrieldev.appcomplect.data.repository.UsuarioRepository
import kotlinx.coroutines.launch

class MainViewModel(private val usuarioRepository: UsuarioRepository) : ViewModel() {

    val usuarioActivo = usuarioRepository.usuarioActivo
    val estaCargando = usuarioRepository.cargando

    init {
        verificarSesion()
    }

    private fun verificarSesion() {
        viewModelScope.launch {
            usuarioRepository.verificarSesion()
        }
    }
}
