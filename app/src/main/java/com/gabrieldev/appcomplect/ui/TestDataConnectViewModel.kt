package com.gabrieldev.appcomplect.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabrieldev.appcomplect.data.remote.UsuarioDataConnectRepository
import com.google.firebase.Timestamp
import com.google.firebase.dataconnect.generated.ObtenerUsuarioPorIdQuery
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.InternalSerializationApi
import java.util.Date

@InternalSerializationApi
class TestDataConnectViewModel(
    private val repository: UsuarioDataConnectRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<TestUiState>(TestUiState.Idle)
    val uiState: StateFlow<TestUiState> = _uiState.asStateFlow()

    fun guardarUsuarioPrueba() {
        viewModelScope.launch {
            _uiState.value = TestUiState.Loading
            val result = repository.guardarUsuario(
                alias = "Tester_${System.currentTimeMillis() % 1000}",
                nombre = "Juan",
                apellidoPaterno = "Perez",
                apellidoMaterno = "Gomez",
                estadoValidacion = true,
                estrellasPrestigio = 10,
                rachaActualDias = 5,
                tipoUsuario = 1,
                ultimaActividad = Timestamp(Date())
            )

            result.fold(
                onSuccess = {
                    _uiState.value = TestUiState.Success("Usuario guardado exitosamente!")
                },
                onFailure = { error ->
                    _uiState.value = TestUiState.Error(error.message ?: "Error al guardar el usuario")
                }
            )
        }
    }

    @InternalSerializationApi
    fun obtenerUsuario(id: String) {
        viewModelScope.launch {
            _uiState.value = TestUiState.Loading
            val result = repository.obtenerUsuarioPorId(id)
            
            result.fold(
                onSuccess = { usuario ->
                    if (usuario != null) {
                        _uiState.value = TestUiState.Success("Usuario encontrado: ${usuario.nombre} ${usuario.apellidoPaterno} - Alias: ${usuario.alias}")
                    } else {
                        _uiState.value = TestUiState.Error("No se encontró ningún usuario con ese ID")
                    }
                },
                onFailure = { error ->
                    _uiState.value = TestUiState.Error(error.message ?: "Error al obtener el usuario")
                }
            )
        }
    }
}

sealed class TestUiState {
    object Idle : TestUiState()
    object Loading : TestUiState()
    data class Success(val message: String) : TestUiState()
    data class Error(val error: String) : TestUiState()
}
