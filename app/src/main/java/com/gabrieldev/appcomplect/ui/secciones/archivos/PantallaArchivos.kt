package com.gabrieldev.appcomplect.ui.secciones.archivos

import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavController.OnDestinationChangedListener
import coil.compose.SubcomposeAsyncImage
import com.gabrieldev.appcomplect.model.Archivo
import com.gabrieldev.appcomplect.model.Usuario
import com.gabrieldev.appcomplect.ui.navegacion.Rutas
import com.gabrieldev.appcomplect.ui.theme.ColorVerde

@Composable
fun PantallaArchivos(
    navController: NavController,
    viewModel: ArchivosViewModel
) {
    val archivos by viewModel.archivos.collectAsState()
    val archivosDivulgacion by viewModel.archivosDivulgacion.collectAsState()
    val cargando by viewModel.cargando.collectAsState()
    val usuarioActivo by viewModel.usuarioActivo.collectAsState()

    var textoBusqueda by remember { mutableStateOf("") }
    var tabSeleccionado by remember { mutableStateOf(0) }

    DisposableEffect(navController) {
        val listener = OnDestinationChangedListener { _, destination, _ ->
            if (destination.route == Rutas.Archivos.ruta) {
                viewModel.cargarArchivos()
            }
        }
        navController.addOnDestinationChangedListener(listener)
        onDispose { navController.removeOnDestinationChangedListener(listener) }
    }

    LaunchedEffect(Unit) {
        viewModel.cargarArchivos()
    }

    val listaActiva = if (tabSeleccionado == 0) archivos else archivosDivulgacion
    val archivosFiltrados = listaActiva.filter {
        it.titulo.contains(textoBusqueda, ignoreCase = true) ||
            it.descripcion.contains(textoBusqueda, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF1F8F1))
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {

        OutlinedTextField(
            value = textoBusqueda,
            onValueChange = { textoBusqueda = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Buscar...", color = Color(0xFF757575)) },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = null, tint = Color(0xFF4CAF50))
            },
            singleLine = true,
            shape = RoundedCornerShape(24.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color(0xFF4CAF50),
                focusedBorderColor = Color(0xFF2E7D32),
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(24.dp))
                .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(24.dp))
                .padding(4.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(20.dp))
                    .background(if (tabSeleccionado == 0) Color(0xFF4CAF50) else Color.Transparent)
                    .clickable { tabSeleccionado = 0 }
                    .padding(vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Archivos",
                    color = if (tabSeleccionado == 0) Color.White else Color(0xFF757575),
                    fontWeight = FontWeight.Bold
                )
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(20.dp))
                    .background(if (tabSeleccionado == 1) Color(0xFF4CAF50) else Color.Transparent)
                    .clickable { tabSeleccionado = 1 }
                    .padding(vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Archivos de Divulgación",
                    color = if (tabSeleccionado == 1) Color.White else Color(0xFF757575),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (tabSeleccionado == 1) {
            Button(
                onClick = { navController.navigate(Rutas.ArchivoCrearDivulgacion.ruta) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
            ) {
                Text("Crear publicación", color = Color.White, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        if (cargando) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color(0xFF4CAF50))
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(archivosFiltrados, key = { it.idArchivo }) { archivo ->
                    if (tabSeleccionado == 1) {
                        ItemArchivoDivulgacion(
                            archivo = archivo,
                            usuarioActual = usuarioActivo,
                            onAbrir = {
                                navController.navigate(Rutas.ArchivoDetalle.conId(archivo.idArchivo))
                            },
                            onEditar = if (usuarioActivo?.uuidSesion == archivo.idAutor) {
                                {
                                    navController.navigate(Rutas.ArchivoEditarDivulgacion.conId(archivo.idArchivo))
                                }
                            } else null,
                            onEliminar = if (usuarioActivo?.uuidSesion == archivo.idAutor) {
                                { viewModel.eliminarArchivoDivulgacion(archivo.idArchivo) { } }
                            } else null
                        )
                    } else {
                        ItemArchivoMockup(archivo = archivo, usuarioActual = usuarioActivo) {
                            navController.navigate(Rutas.ArchivoDetalle.conId(archivo.idArchivo))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ItemArchivoMockup(
    archivo: Archivo,
    usuarioActual: Usuario?,
    onClick: () -> Unit
) {
    val context = LocalContext.current
    val nivelArchivo = archivo.nivelRequerido
    val nivelUsuario = usuarioActual?.nivel?.jerarquia ?: 1
    val bloqueado = nivelUsuario < nivelArchivo
    val numeroNivelDisplay = nivelArchivo

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(enabled = !bloqueado) { onClick() }
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .size(96.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFE8F5E9)),
                    contentAlignment = Alignment.Center
                ) {
                    if (!archivo.imagenUrl.isNullOrEmpty()) {
                        SubcomposeAsyncImage(
                            model = archivo.imagenUrl,
                            contentDescription = "Portada",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        Text("📦", fontSize = 40.sp)
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {

                        Box(
                            modifier = Modifier
                                .background(
                                    if (bloqueado) Color(0xFFEEEEEE) else Color(0xFFE8F5E9),
                                    RoundedCornerShape(8.dp)
                                )
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Nivel $numeroNivelDisplay",
                                color = if (bloqueado) Color.Gray else Color(0xFF2E7D32),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.width(6.dp))

                        if (bloqueado) {
                            Icon(
                                Icons.Default.Lock,
                                contentDescription = "Bloqueado",
                                tint = Color.Gray,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = archivo.titulo,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = if (bloqueado) Color(0xFF9E9E9E) else Color(0xFF2E7D32),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = "Por ${archivo.autor}",
                        style = MaterialTheme.typography.bodySmall,
                        color = if (bloqueado) Color(0xFFBDBDBD) else Color(0xFF757575)
                    )

                    if (!archivo.tema.isNullOrBlank()) {
                        Text(
                            text = "Tema: ${archivo.tema}",
                            style = MaterialTheme.typography.labelSmall,
                            color = ColorVerde,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = archivo.descripcion,
                        style = MaterialTheme.typography.bodySmall,
                        color = if (bloqueado) Color(0xFFBDBDBD) else Color.DarkGray,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            if (!bloqueado) {
                HorizontalDivider(color = Color(0xFFEEEEEE))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = {
                        Toast.makeText(context, "Descargando: ${archivo.titulo}", Toast.LENGTH_SHORT).show()
                    }) {
                        Icon(
                            Icons.Default.Download,
                            contentDescription = "Descargar",
                            tint = Color(0xFF2E7D32),
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(Modifier.width(4.dp))
                        Text("Descargar", color = Color(0xFF2E7D32))
                    }
                }
            }
        }
    }
}

@Composable
fun ItemArchivoDivulgacion(
    archivo: Archivo,
    usuarioActual: Usuario?,
    onAbrir: () -> Unit,
    onEditar: (() -> Unit)?,
    onEliminar: (() -> Unit)?
) {
    val context = LocalContext.current
    val nivelArchivo = archivo.nivelRequerido
    val nivelUsuario = usuarioActual?.nivel?.jerarquia ?: 1
    val esAutor = usuarioActual?.uuidSesion == archivo.idAutor
    val bloqueado = nivelUsuario < nivelArchivo && !esAutor
    var mostrarEliminar by remember(archivo.idArchivo) { mutableStateOf(false) }

    if (mostrarEliminar) {
        AlertDialog(
            onDismissRequest = { mostrarEliminar = false },
            title = { Text("Eliminar publicación") },
            text = { Text("¿Seguro que quieres eliminar \"${archivo.titulo}\"? Esta acción no se puede deshacer.") },
            confirmButton = {
                Button(
                    onClick = {
                        mostrarEliminar = false
                        onEliminar?.invoke()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC62828))
                ) { Text("Eliminar", color = Color.White) }
            },
            dismissButton = {
                TextButton(onClick = { mostrarEliminar = false }) { Text("Cancelar") }
            }
        )
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(enabled = !bloqueado) { onAbrir() }
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .size(96.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFE8F5E9)),
                    contentAlignment = Alignment.Center
                ) {
                    if (!archivo.imagenUrl.isNullOrEmpty()) {
                        SubcomposeAsyncImage(
                            model = archivo.imagenUrl,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        Text("📦", fontSize = 40.sp)
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .background(
                                    if (bloqueado) Color(0xFFEEEEEE) else Color(0xFFE8F5E9),
                                    RoundedCornerShape(8.dp)
                                )
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "Nivel $nivelArchivo",
                                color = if (bloqueado) Color.Gray else Color(0xFF2E7D32),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        if (bloqueado) {
                            Spacer(Modifier.width(6.dp))
                            Icon(Icons.Default.Lock, null, Modifier.size(16.dp), tint = Color.Gray)
                        }
                    }
                    Spacer(Modifier.height(6.dp))
                    Text(
                        archivo.titulo,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = if (bloqueado) Color(0xFF9E9E9E) else Color(0xFF2E7D32),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        "Por ${archivo.autor}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF757575)
                    )
                    if (!archivo.tema.isNullOrBlank()) {
                        Text(
                            text = "Tema: ${archivo.tema}",
                            style = MaterialTheme.typography.labelSmall,
                            color = ColorVerde,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Text(
                        archivo.descripcion,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.DarkGray,
                        maxLines = 2
                    )
                }
            }

            HorizontalDivider(color = Color(0xFFEEEEEE))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {

                if (!bloqueado) {
                    TextButton(onClick = {
                        Toast.makeText(context, "Descargando: ${archivo.titulo}", Toast.LENGTH_SHORT).show()
                    }) {
                        Icon(Icons.Default.Download, null, tint = Color(0xFF2E7D32), modifier = Modifier.size(20.dp))
                        Spacer(Modifier.width(4.dp))
                        Text("Descargar", color = Color(0xFF2E7D32))
                    }
                }
                
                if (onEditar != null) {
                    TextButton(onClick = onEditar) {
                        Icon(Icons.Default.Edit, null, tint = Color(0xFF2E7D32), modifier = Modifier.size(20.dp))
                        Spacer(Modifier.width(4.dp))
                        Text("Editar", color = Color(0xFF2E7D32))
                    }
                }
                if (onEliminar != null) {
                    TextButton(onClick = { mostrarEliminar = true }) {
                        Icon(Icons.Default.Delete, null, tint = Color(0xFFC62828), modifier = Modifier.size(20.dp))
                        Spacer(Modifier.width(4.dp))
                        Text("Eliminar", color = Color(0xFFC62828))
                    }
                }
            }
        }
    }
}
