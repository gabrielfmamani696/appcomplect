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
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroUsuario(
    viewModel: RegistroViewModel,
    alTerminar: () -> Unit
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = if(state.esModoLogin) "Iniciar Sesión" else "¡Bienvenido a tu Aventura!",
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = if(state.esModoLogin) "Ingresa tus datos recuperar perfil" else "Te convertirás en un gran investigador",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(24.dp))

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
        Spacer(modifier = Modifier.height(16.dp))

        if (!state.esModoLogin) {
            Text("Elige tu avatar:", style = MaterialTheme.typography.titleMedium)
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
                        loading = {
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                CircularProgressIndicator(modifier = Modifier.size(24.dp))
                            }
                        },
                        error = {
                            Icon(Icons.Default.Person, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(40.dp))
                        }
                    )
                }
            }
            if (state.avatarSeleccionado != null) {
                Text(
                    text = state.avatarSeleccionado!!.descripcion,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            } else {
                Spacer(modifier = Modifier.height(16.dp))
            }


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
            
            Spacer(modifier = Modifier.height(16.dp))
        }

        OutlinedTextField(
            value = state.numeroCelular,
            onValueChange = {
                if (it.all { char -> char.isDigit() }) {
                    viewModel.actualizarCampo("numeroCelular", it)
                }
            },
            label = { Text("Número de Celular") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        if (!state.esModoLogin && state.listaRoles.isNotEmpty()) {
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

        if (!state.esModoLogin) {
            Spacer(modifier = Modifier.height(16.dp))
            Text("Tu nombre de investigador (Alias):", style = MaterialTheme.typography.titleMedium)
            OutlinedTextField(
                value = state.alias,
                onValueChange = { viewModel.actualizarCampo("alias", it) },
                label = { Text("Alias") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
        }

        if (state.mensajeError != null) {
            Text(
                text = state.mensajeError!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { viewModel.intentarRegistroOLogin(alTerminar) },
            enabled = !state.cargando,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            if (state.cargando) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = Color.White
                )
            } else {
                Text(
                    text = if(state.esModoLogin) "Ingresar" else "Comenzar",
                    fontSize = 18.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = { viewModel.toggleModoLogin() }) {
            Text(
                text = if(state.esModoLogin) "¿No tienes cuenta? Regístrate aquí" else "¿Ya tienes una cuenta? Inicia Sesión",
                color = MaterialTheme.colorScheme.primary
            )
        }
        
        Spacer(modifier = Modifier.height(32.dp))
    }
}
