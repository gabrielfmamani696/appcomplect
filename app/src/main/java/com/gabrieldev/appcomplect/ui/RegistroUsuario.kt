package com.gabrieldev.appcomplect.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gabrieldev.appcomplect.data.repository.AvatarRepository
import com.gabrieldev.appcomplect.data.repository.UsuarioRepository
import com.gabrieldev.appcomplect.model.OpcionAvatar
import com.gabrieldev.appcomplect.model.Usuario
import kotlinx.coroutines.launch
import kotlinx.serialization.InternalSerializationApi

@OptIn(InternalSerializationApi::class)
@Composable
fun RegistroUsuario(
    usuarioRepository: UsuarioRepository,
    avatarRepository: AvatarRepository,
    alTerminar: () -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var apellidoPaterno by remember { mutableStateOf("") }
    var apellidoMaterno by remember { mutableStateOf("") }
    var alias by remember { mutableStateOf("") }
    var avatarSeleccionado by remember { mutableStateOf<OpcionAvatar?>(null) }
    var listaAvatares by remember { mutableStateOf<List<OpcionAvatar>>(emptyList()) }
    var mensajeError by remember { mutableStateOf<String?>(null) }
    
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    // Cargar avatares al iniciar

    LaunchedEffect(Unit) {
        listaAvatares = avatarRepository.obtenerAvatares()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "¡Bienvenido a tu Aventura!",
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = "Te convertirás en un gran periodista",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(24.dp))

        // avatares
        Text("Elige tu avatar:", style = MaterialTheme.typography.titleMedium)
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(listaAvatares) { avatar ->
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .border(
                            width = if (avatarSeleccionado?.idAvatar == avatar.idAvatar) 4.dp else 1.dp,
                            color = if (avatarSeleccionado?.idAvatar == avatar.idAvatar) MaterialTheme.colorScheme.primary else Color.Gray,
                            shape = CircleShape
                        )
                        .clickable { avatarSeleccionado = avatar },
                    contentAlignment = Alignment.Center
                ) {
                    // Aquí iría un AsyncImage de Coil. Por ahora usamos texto como fallback
                    Text(text = avatar.descripcion.take(1), style = MaterialTheme.typography.headlineSmall)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))


        // nombre
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it; mensajeError = null },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        //a paterno
        OutlinedTextField(
            value = apellidoPaterno,
            onValueChange = { apellidoPaterno = it; mensajeError = null },
            label = { Text("Apellido Paterno") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        //a materno
        OutlinedTextField(
            value = apellidoMaterno,
            onValueChange = { apellidoMaterno = it; mensajeError = null },
            label = { Text("Apellido Materno (Opcional)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // list de alias
        Text("Tu nombre de periodista (Alias):", style = MaterialTheme.typography.titleMedium)
        OutlinedTextField(
            value = alias,
            onValueChange = { alias = it; mensajeError = null },
            label = { Text("Alias") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            trailingIcon = {
                IconButton(onClick = {
                    alias = "Periodista${(1000..9999).random()}"
                }) {
                    Text("🎲", fontSize = 20.sp)
                }
            }
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextButton(
                onClick = {
                    if (nombre.isNotBlank() && apellidoPaterno.isNotBlank()) {
                        alias = (nombre.take(3) + apellidoPaterno.take(3)).lowercase()
                    }
                },
                enabled = nombre.isNotBlank() && apellidoPaterno.isNotBlank()
            ) {
                Text("Sugerir desde mi nombre")
            }
        }

        if (mensajeError != null) {
            Text(
                text = mensajeError!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (nombre.length < 4) {
                    mensajeError = "El nombre es muy corto"
                } else if (alias.length < 4) {
                    mensajeError = "El alias debe tener al menos 4 letras"
                } else if (avatarSeleccionado == null) {
                    mensajeError = "Por favor selecciona un avatar"
                } else {
                    scope.launch {
                        val nuevoUsuario = Usuario(
                            nombre = nombre,
                            apellidoPaterno = apellidoPaterno,
                            apellidoMaterno = apellidoMaterno,
                            alias = alias,
                            idAvatar = avatarSeleccionado!!.idAvatar,
                            tipoUsuario = 3
                        )
                        usuarioRepository.registrarUsuario(nuevoUsuario)
                        alTerminar()
                    }
                }
            },
            modifier = Modifier.fillMaxWidth().height(56.dp)
        ) {
            Text("¡Comenzar mi aventura!")
        }
    }
}
