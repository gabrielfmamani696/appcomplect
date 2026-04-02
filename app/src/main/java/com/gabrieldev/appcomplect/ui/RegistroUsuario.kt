package com.gabrieldev.appcomplect.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.gabrieldev.appcomplect.data.repository.AvatarRepository
import com.gabrieldev.appcomplect.data.repository.UsuarioRepository
import com.gabrieldev.appcomplect.model.OpcionAvatar
import com.gabrieldev.appcomplect.model.RolUsuario
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
    var esModoLogin by remember { mutableStateOf(false) }
    
    var nombre by remember { mutableStateOf("") }
    var apellidoPaterno by remember { mutableStateOf("") }
    var apellidoMaterno by remember { mutableStateOf("") }
    var alias by remember { mutableStateOf("") }
    var avatarSeleccionado by remember { mutableStateOf<OpcionAvatar?>(null) }
    var listaAvatares by remember { mutableStateOf<List<OpcionAvatar>>(emptyList()) }
    var mensajeError by remember { mutableStateOf<String?>(null) }
    
    var numeroCelular by remember { mutableStateOf("") }
    var curso by remember { mutableStateOf("") }
    var paralelo by remember { mutableStateOf("") }
    var nombreColegio by remember { mutableStateOf("") }
    var rolSeleccionado by remember { mutableStateOf<RolUsuario?>(null) }
    var listaRoles by remember { mutableStateOf<List<RolUsuario>>(emptyList()) }

    var cargando by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        listaAvatares = avatarRepository.obtenerAvatares()
        listaRoles = usuarioRepository.obtenerRoles()
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
            text = if(esModoLogin) "Iniciar Sesión" else "¡Bienvenido a tu Aventura!",
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = if(esModoLogin) "Ingresa tus datos recuperar perfil" else "Te convertirás en un gran investigador",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (!esModoLogin) {
            Text("Elige tu avatar:", style = MaterialTheme.typography.titleMedium)
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(listaAvatares) { avatar ->
                    SubcomposeAsyncImage(
                        model = avatar.urlImagen,
                        contentDescription = avatar.descripcion,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .border(
                                width = if (avatarSeleccionado?.idAvatar == avatar.idAvatar) 4.dp else 1.dp,
                                color = if (avatarSeleccionado?.idAvatar == avatar.idAvatar) MaterialTheme.colorScheme.primary else Color.Gray,
                                shape = CircleShape
                            )
                            .clickable { avatarSeleccionado = avatar },
                        loading = {
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                CircularProgressIndicator(modifier = Modifier.size(32.dp), color = MaterialTheme.colorScheme.primary, strokeWidth = 2.dp)
                            }
                        },
                        error = {
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                Icon(imageVector = Icons.Default.Person, contentDescription = null, modifier = Modifier.size(40.dp), tint = Color.Gray)
                            }
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        OutlinedTextField(
            value = numeroCelular,
            onValueChange = { 
                val filtered = it.filter { char -> char.isDigit() }
                if (filtered.length <= 15) {
                    numeroCelular = filtered 
                }
                mensajeError = null 
            },
            label = { Text("Número de Celular") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            isError = numeroCelular.isNotEmpty() && numeroCelular.length < 8,
            supportingText = { if (numeroCelular.isNotEmpty() && numeroCelular.length < 8) Text("Mínimo 8 dígitos") }
        )

        OutlinedTextField(
            value = nombre,
            onValueChange = { 
                val filtered = it.filter { char -> char.isLetter() || char.isWhitespace() }
                nombre = filtered
                mensajeError = null 
            },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = nombre.isNotEmpty() && nombre.trim().length < 3,
            supportingText = { if (nombre.isNotEmpty() && nombre.trim().length < 3) Text("Mínimo 3 letras") }
        )

        OutlinedTextField(
            value = apellidoPaterno,
            onValueChange = { 
                val filtered = it.filter { char -> char.isLetter() || char.isWhitespace() }
                apellidoPaterno = filtered
                mensajeError = null 
            },
            label = { Text("Apellido Paterno") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = apellidoPaterno.isNotEmpty() && apellidoPaterno.trim().length < 2,
            supportingText = { if (apellidoPaterno.isNotEmpty() && apellidoPaterno.trim().length < 2) Text("Mínimo 2 letras") }
        )

        if (!esModoLogin) {
            OutlinedTextField(
                value = apellidoMaterno,
                onValueChange = { apellidoMaterno = it; mensajeError = null },
                label = { Text("Apellido Materno (Opcional)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = nombreColegio,
                onValueChange = { nombreColegio = it; mensajeError = null },
                label = { Text("Nombre del Colegio (Opcional)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = curso,
                    onValueChange = { curso = it; mensajeError = null },
                    label = { Text("Curso (Opcional)") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
                OutlinedTextField(
                    value = paralelo,
                    onValueChange = { paralelo = it; mensajeError = null },
                    label = { Text("Paralelo (Opc)") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
            }

            if (listaRoles.isNotEmpty()) {
                Text("Soy un:", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 16.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                    listaRoles.forEach { rol ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable { rolSeleccionado = rol }
                        ) {
                            RadioButton(
                                selected = (rolSeleccionado?.id == rol.id),
                                onClick = { rolSeleccionado = rol }
                            )
                            Text(text = rol.nombreRol)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Tu nombre de investigador (Alias):", style = MaterialTheme.typography.titleMedium)
            OutlinedTextField(
                value = alias,
                onValueChange = { alias = it; mensajeError = null },
                label = { Text("Alias") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                trailingIcon = {
                    IconButton(onClick = {
                        alias = "Investigador${(1000..9999).random()}"
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
                if (numeroCelular.length < 8) {
                    mensajeError = "Ingresa un número de celular válido"
                } else if (nombre.length < 4) {
                    mensajeError = "El nombre es muy corto"
                } else if (apellidoPaterno.length < 2) {
                    mensajeError = "El apellido paterno es muy corto"
                } else if (!esModoLogin) {
                    if (rolSeleccionado == null) {
                        mensajeError = "Selecciona si eres Estudiante o Docente"
                    } else if (alias.length < 4) {
                        mensajeError = "El alias debe tener al menos 4 letras"
                    } else if (avatarSeleccionado == null) {
                        mensajeError = "Por favor selecciona un avatar"
                    } else {
                        cargando = true
                        scope.launch {
                            try {
                                val nuevoUsuario = Usuario(
                                    nombre = nombre,
                                    apellidoPaterno = apellidoPaterno,
                                    apellidoMaterno = apellidoMaterno,
                                    alias = alias,
                                    idAvatar = avatarSeleccionado!!.idAvatar,
                                    numeroCelular = numeroCelular,
                                    curso = curso.takeIf { it.isNotBlank() },
                                    paralelo = paralelo.takeIf { it.isNotBlank() },
                                    nombreColegio = nombreColegio.takeIf { it.isNotBlank() },
                                    idRol = rolSeleccionado!!.id,
                                    idNivel = "7b80db26-06f0-4e45-a2da-56d0c092131f"
                                )
                                usuarioRepository.registrarUsuario(nuevoUsuario)
                                cargando = false
                                alTerminar()
                            } catch (e: Exception) {
                                e.printStackTrace()
                                cargando = false
                                if (e.message?.contains("usuario_numeroCelular_uidx") == true) {
                                    mensajeError = "Este número de celular ya está registrado. Inicia sesión en su lugar."
                                } else {
                                    mensajeError = "Hubo un error al crear la cuenta. Inténtalo de nuevo."
                                }
                            }
                        }
                    }
                } else {
                    cargando = true
                    scope.launch {
                        val exito = usuarioRepository.iniciarSesionExistente(
                            numeroCelular = numeroCelular,
                            nombre = nombre,
                            apellidoPaterno = apellidoPaterno
                        )
                        cargando = false
                        if (exito) {
                            alTerminar()
                        } else {
                            mensajeError = "Credenciales incorrectas o cuenta no registrada"
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            enabled = !cargando
        ) {
            if (cargando) CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
            else Text(if (esModoLogin) "Ingresar" else "¡Comenzar mi aventura!")
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = { 
            esModoLogin = !esModoLogin 
            mensajeError = null
        }) {
            Text(if (esModoLogin) "No tengo perfil, quiero crear uno nuevo" else "Ya tengo un investigador, Iniciar sesión")
        }
    }
}
