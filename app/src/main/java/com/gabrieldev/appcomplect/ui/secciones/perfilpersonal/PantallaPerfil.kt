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
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.gabrieldev.appcomplect.model.Usuario

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaPerfil(
    usuario: Usuario,
    viewModel: PerfilViewModel
) {
    val state by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    val opcionesCurso = listOf(
        "Primero de Primaria",
        "Segundo de Primaria",
        "Tercero de Primaria",
        "Cuarto de Primaria",
        "Quinto de Primaria",
        "Sexto de Primaria"
    )

    LaunchedEffect(usuario) {
        viewModel.initUsuario(usuario)
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
                items(state.listaAvatares) { avatar ->
                    SubcomposeAsyncImage(
                        model = avatar.urlImagen,
                        contentDescription = avatar.descripcion,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .border(
                                width = if (state.avatarSeleccionado?.idAvatar == avatar.idAvatar) 4.dp else 1.dp,
                                color = if (state.avatarSeleccionado?.idAvatar == avatar.idAvatar) MaterialTheme.colorScheme.primary else Color.Gray,
                                shape = CircleShape
                            )
                            .clickable { viewModel.seleccionarAvatar(avatar) },
                        loading = { CircularProgressIndicator(modifier = Modifier.size(32.dp)) },
                        error = { Icon(Icons.Default.Person, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(40.dp)) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = state.nombre,
                onValueChange = { viewModel.actualizarCampo("nombre", it) },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = state.apellidoPaterno,
                onValueChange = { viewModel.actualizarCampo("apellidoPaterno", it) },
                label = { Text("Apellido Paterno") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = state.apellidoMaterno,
                onValueChange = { viewModel.actualizarCampo("apellidoMaterno", it) },
                label = { Text("Apellido Materno") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = state.nombreColegio,
                onValueChange = { viewModel.actualizarCampo("nombreColegio", it) },
                label = { Text("Nombre del Colegio") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                ExposedDropdownMenuBox(
                    expanded = state.expandedCurso,
                    onExpandedChange = { viewModel.toggleExpandedCurso() },
                    modifier = Modifier.weight(1f)
                ) {
                    OutlinedTextField(
                        value = state.curso,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Curso") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = state.expandedCurso) },
                        colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                        modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable, true),
                        singleLine = true
                    )
                    ExposedDropdownMenu(
                        expanded = state.expandedCurso,
                        onDismissRequest = { viewModel.toggleExpandedCurso() }
                    ) {
                        opcionesCurso.forEach { opcion ->
                            DropdownMenuItem(
                                text = { Text(opcion) },
                                onClick = {
                                    viewModel.actualizarCampo("curso", opcion)
                                    viewModel.toggleExpandedCurso()
                                }
                            )
                        }
                    }
                }
                OutlinedTextField(
                    value = state.paralelo,
                    onValueChange = { viewModel.actualizarCampo("paralelo", it) },
                    label = { Text("Paralelo") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
            }

            OutlinedTextField(
                value = state.numeroCelular,
                onValueChange = { viewModel.actualizarCampo("numeroCelular", it) },
                label = { Text("Número de Celular") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))
            
            Text("Preferencia Notificación Diaria:", style = MaterialTheme.typography.titleMedium)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = state.horaH,
                    onValueChange = { if(it.length <= 2) viewModel.actualizarCampo("horaH", it.filter { c -> c.isDigit() }) },
                    label = { Text("Hora (00-23)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
                OutlinedTextField(
                    value = state.horaM,
                    onValueChange = { if(it.length <= 2) viewModel.actualizarCampo("horaM", it.filter { c -> c.isDigit() }) },
                    label = { Text("Minuto (00-59)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
            }

            if (state.listaRoles.isNotEmpty()) {
                Text("Soy un:", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 16.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                    state.listaRoles.forEach { rol ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable { viewModel.seleccionarRol(rol) }
                        ) {
                            RadioButton(
                                selected = (state.rolSeleccionado?.id == rol.id),
                                onClick = { viewModel.seleccionarRol(rol) }
                            )
                            Text(text = rol.nombreRol)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Tu nombre de investigador (Alias):", style = MaterialTheme.typography.titleMedium)
            OutlinedTextField(
                value = state.alias,
                onValueChange = { viewModel.actualizarCampo("alias", it) },
                label = { Text("Alias") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            if (state.mensajeError != null) {
                Text(
                    text = state.mensajeError!!,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            if (state.mensajeExito != null) {
                Text(
                    text = state.mensajeExito!!,
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
                    onClick = { viewModel.cerrarSesion() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F)),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Cerrar Sesión", color = Color.White)
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = { viewModel.guardarCambios() },
                    enabled = !state.guardando,
                    modifier = Modifier.weight(1f)
                ) {
                    if (state.guardando) {
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
