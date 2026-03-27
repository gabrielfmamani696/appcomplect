package com.gabrieldev.appcomplect.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gabrieldev.appcomplect.data.remote.UsuarioDataConnectRepository
import com.google.firebase.dataconnect.generated.DefaultConnector
import kotlinx.serialization.InternalSerializationApi
import java.util.UUID

// Factory básico por si no usas Hilt/Dagger

class TestDataConnectViewModelFactory(private val connector: DefaultConnector) :
    ViewModelProvider.Factory {
    @InternalSerializationApi
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TestDataConnectViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TestDataConnectViewModel(UsuarioDataConnectRepository(connector)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

@Composable
@InternalSerializationApi
fun TestDataConnectScreen(
    connector: DefaultConnector,
    modifier: Modifier = Modifier,
    viewModel: TestDataConnectViewModel = viewModel(factory = TestDataConnectViewModelFactory(connector))
) {
    val uiState by viewModel.uiState.collectAsState()
    var inputId by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "Prueba de Firebase Data Connect", style = MaterialTheme.typography.titleLarge)

        Button(
            onClick = { viewModel.guardarUsuarioPrueba() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar Usuario de Prueba")
        }

        OutlinedTextField(
            value = inputId,
            onValueChange = { inputId = it },
            label = { Text("ID de Usuario a buscar (UUID)") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                if (inputId.isNotBlank()) {
                    viewModel.obtenerUsuario(inputId)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Obtener Usuario por ID")
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (val state = uiState) {
            is TestUiState.Idle -> Text("Esperando acción...")
            is TestUiState.Loading -> CircularProgressIndicator()
            is TestUiState.Success -> Text(text = state.message, color = MaterialTheme.colorScheme.primary)
            is TestUiState.Error -> Text(text = state.error, color = MaterialTheme.colorScheme.error)
        }
    }
}
