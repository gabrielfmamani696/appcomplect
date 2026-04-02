package com.gabrieldev.appcomplect.ui.secciones.perfilpersonal

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import coil.compose.SubcomposeAsyncImage
import com.gabrieldev.appcomplect.data.repository.AvatarRepository
import com.gabrieldev.appcomplect.data.repository.UsuarioRepository
import com.gabrieldev.appcomplect.model.OpcionAvatar
import com.gabrieldev.appcomplect.model.RolUsuario
import com.gabrieldev.appcomplect.model.Usuario
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaPerfil(
    usuario: Usuario,
    usuarioRepository: UsuarioRepository,
    avatarRepository: AvatarRepository
) {
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    var nombre by remember { mutableStateOf(usuario.nombre) }
    var apellidoPaterno by remember { mutableStateOf(usuario.apellidoPaterno) }
    var apellidoMaterno by remember { mutableStateOf(usuario.apellidoMaterno) }
    var alias by remember { mutableStateOf(usuario.alias) }
    
    var numeroCelular by remember { mutableStateOf(usuario.numeroCelular) }
    var curso by remember { mutableStateOf(usuario.curso ?: "") }
    var paralelo by remember { mutableStateOf(usuario.paralelo ?: "") }
    var nombreColegio by remember { mutableStateOf(usuario.nombreColegio ?: "") }
    
    val horaParts = usuario.horaNotificacion?.split(":") ?: listOf("18", "00")
    var horaNotificacionH by remember { mutableStateOf(horaParts.getOrNull(0) ?: "18") }
    var horaNotificacionM by remember { mutableStateOf(horaParts.getOrNull(1) ?: "00") }

    var avatarSeleccionado by remember { mutableStateOf<OpcionAvatar?>(null) }
    var listaAvatares by remember { mutableStateOf<List<OpcionAvatar>>(emptyList()) }
    var rolSeleccionado by remember { mutableStateOf<RolUsuario?>(null) }
    var listaRoles by remember { mutableStateOf<List<RolUsuario>>(emptyList()) }

    var mensajeError by remember { mutableStateOf<String?>(null) }
    var mensajeExito by remember { mutableStateOf<String?>(null) }
    var guardando by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        listaAvatares = avatarRepository.obtenerAvatares()
        listaRoles = usuarioRepository.obtenerRoles()
        
        avatarSeleccionado = listaAvatares.find { it.idAvatar == usuario.idAvatar }
        rolSeleccionado = listaRoles.find { it.id == usuario.idRol }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mi Perfil", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF2E7D32))
            )
        },
        containerColor = Color(0xFFF1F8F1)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            
            Text("Tu avatar:", style = MaterialTheme.typography.titleMedium)
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
                            CircularProgressIndicator(modifier = Modifier.size(32.dp))
                        },
                        error = {
                            Icon(Icons.Default.Person, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(40.dp))
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it; mensajeError = null; mensajeExito = null },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = apellidoPaterno,
                onValueChange = { apellidoPaterno = it; mensajeError = null; mensajeExito = null },
                label = { Text("Apellido Paterno") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = apellidoMaterno,
                onValueChange = { apellidoMaterno = it; mensajeError = null; mensajeExito = null },
                label = { Text("Apellido Materno (Opcional)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = nombreColegio,
                onValueChange = { nombreColegio = it; mensajeError = null; mensajeExito = null },
                label = { Text("Nombre del Colegio (Opcional)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = curso,
                    onValueChange = { curso = it; mensajeError = null; mensajeExito = null },
                    label = { Text("Curso") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
                OutlinedTextField(
                    value = paralelo,
                    onValueChange = { paralelo = it; mensajeError = null; mensajeExito = null },
                    label = { Text("Paralelo") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
            }

            OutlinedTextField(
                value = numeroCelular,
                onValueChange = { numeroCelular = it; mensajeError = null; mensajeExito = null },
                label = { Text("Número de Celular") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))
            
            Text("Preferencia Notificación Diaria:", style = MaterialTheme.typography.titleMedium)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = horaNotificacionH,
                    onValueChange = { if(it.length <= 2) horaNotificacionH = it.filter { c -> c.isDigit() } ; mensajeError = null; mensajeExito = null },
                    label = { Text("Hora (00-23)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
                OutlinedTextField(
                    value = horaNotificacionM,
                    onValueChange = { if(it.length <= 2) horaNotificacionM = it.filter { c -> c.isDigit() } ; mensajeError = null; mensajeExito = null },
                    label = { Text("Minuto (00-59)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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
                onValueChange = { alias = it; mensajeError = null; mensajeExito = null },
                label = { Text("Alias") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            if (mensajeError != null) {
                Text(
                    text = mensajeError!!,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            if (mensajeExito != null) {
                Text(
                    text = mensajeExito!!,
                    color = Color(0xFF2E7D32),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        scope.launch {
                            usuarioRepository.cerrarSesion()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F)),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Cerrar Sesión", color = Color.White)
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = {
                        if (nombre.length < 4) {
                            mensajeError = "El nombre es muy corto"
                        } else if (numeroCelular.length < 8) {
                            mensajeError = "Ingresa un número de celular válido"
                        } else if (rolSeleccionado == null) {
                            mensajeError = "Selecciona si eres Estudiante o Docente"
                        } else if (alias.length < 4) {
                            mensajeError = "El alias debe tener al menos 4 letras"
                        } else {
                            val h = horaNotificacionH.toIntOrNull() ?: 18
                            val m = horaNotificacionM.toIntOrNull() ?: 0
                            val horaLimpia = "${h.toString().padStart(2, '0')}:${m.toString().padStart(2, '0')}"
                            
                            guardando = true
                            scope.launch {
                                val upd = usuario.copy(
                                    nombre = nombre,
                                    apellidoPaterno = apellidoPaterno,
                                    apellidoMaterno = apellidoMaterno,
                                    alias = alias,
                                    idAvatar = avatarSeleccionado?.idAvatar ?: usuario.idAvatar,
                                    numeroCelular = numeroCelular,
                                    curso = curso.takeIf { it.isNotBlank() },
                                    paralelo = paralelo.takeIf { it.isNotBlank() },
                                    nombreColegio = nombreColegio.takeIf { it.isNotBlank() },
                                    idRol = rolSeleccionado!!.id,
                                    horaNotificacion = horaLimpia
                                )
                                val exito = usuarioRepository.actualizarUsuarioPerfil(upd)
                                guardando = false
                                if (exito) {
                                    mensajeExito = "Perfil actualizado correctamente."
                                } else {
                                    mensajeError = "Hubo un error al actualizar."
                                }
                            }
                        }
                    },
                    enabled = !guardando,
                    modifier = Modifier.weight(1f)
                ) {
                    if (guardando) {
                        CircularProgressIndicator(modifier = Modifier.size(20.dp), color = Color.White)
                    } else {
                        Text("Guardar Cambios")
                    }
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
