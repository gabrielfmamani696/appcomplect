package com.gabrieldev.appcomplect.ui.secciones.archivos

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.School
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
import androidx.compose.material3.Surface
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.NavController.OnDestinationChangedListener
import coil.compose.SubcomposeAsyncImage
import com.gabrieldev.appcomplect.model.Archivo
import com.gabrieldev.appcomplect.model.EspacioAprendizaje
import com.gabrieldev.appcomplect.model.EstadisticasArchivo
import com.gabrieldev.appcomplect.model.Usuario
import com.gabrieldev.appcomplect.ui.navegacion.Rutas
import com.gabrieldev.appcomplect.ui.theme.ColorVerde
import com.gabrieldev.appcomplect.ui.theme.ColorVerdeClaro
import com.gabrieldev.appcomplect.ui.theme.ColorVerdeMedio
import java.util.UUID

@Composable
fun PantallaArchivos(
    navController: NavController,
    viewModel: ArchivosViewModel
) {
    val context = LocalContext.current

    val archivos by viewModel.archivos.collectAsState()
    val archivosDivulgacion by viewModel.archivosDivulgacion.collectAsState()
    val archivosDescargados by viewModel.archivosDescargados.collectAsState()
    val misEspacios by viewModel.misEspacios.collectAsState()
    val estadisticas by viewModel.estadisticasSeleccionadas.collectAsState()
    val cargando by viewModel.cargando.collectAsState()
    val usuarioActivo by viewModel.usuarioActivo.collectAsState()

    var textoBusqueda by remember { mutableStateOf("") }
    var pestanaSeleccionada by remember { mutableStateOf(0) }
    var codigoAcceso by remember { mutableStateOf("") }
    var mostrarModalEspacios by remember { mutableStateOf(false) }
    var archivoParaEstadisticas by remember { mutableStateOf<Archivo?>(null) }

    val misEspaciosIds = misEspacios.map { it.id }.toSet()
    val esDocente = usuarioActivo?.nombreRol?.lowercase()?.contains("docente") == true

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

    val listaUsadaActual = when (pestanaSeleccionada) {
        0 -> archivos
        1 -> if (esDocente) archivos.filter { it.idUsuarioAutor == usuarioActivo?.uuidSesion } else archivosDivulgacion
        2 -> archivosDescargados
        else -> archivos
    }

    val archivosFiltrados = listaUsadaActual.filter {
        it.titulo.contains(textoBusqueda, ignoreCase = true) ||
        it.descripcion.contains(textoBusqueda, ignoreCase = true)
    }

    if (mostrarModalEspacios) {
        Dialog(onDismissRequest = { mostrarModalEspacios = false }) {
            ModalEspaciosAprendizaje(
                espacios = misEspacios,
                archivos = archivos.filter { it.idUsuarioAutor == usuarioActivo?.uuidSesion },
                usuarioId = usuarioActivo?.uuidSesion,
                onActualizarArchivoEspacio = { archivoId, espacioId ->
                    viewModel.actualizarEspacioArchivo(archivoId, espacioId) { ok ->
                        if (ok) {
                            Toast.makeText(context, "Relación actualizada.", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "Error al actualizar relación.", Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                onDismiss = { mostrarModalEspacios = false },
                onCrearEspacio = { nombre ->
                    viewModel.crearEspacio(nombre) { codigo ->
                        if (codigo != null) {
                            Toast.makeText(context, "Espacio creado: $codigo", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "Error al crear espacio. Inténtalo de nuevo.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            )
        }
    }

    archivoParaEstadisticas?.let { archivo ->
        LaunchedEffect(archivo.idArchivo) {
            viewModel.cargarEstadisticas(archivo.idArchivo)
        }
        Dialog(onDismissRequest = { archivoParaEstadisticas = null }) {
            ModalEstadisticasArchivo(
                archivo = archivo,
                estadisticas = estadisticas,
                onDismiss = { archivoParaEstadisticas = null }
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
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
                    .background(if (pestanaSeleccionada == 0) Color(0xFF4CAF50) else Color.Transparent)
                    .clickable { pestanaSeleccionada = 0 }
                    .padding(vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Archivos Confidenciales",
                    color = if (pestanaSeleccionada == 0) Color.White else Color(0xFF757575),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center
                )
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(20.dp))
                    .background(if (pestanaSeleccionada == 1) Color(0xFF4CAF50) else Color.Transparent)
                    .clickable { pestanaSeleccionada = 1 }
                    .padding(vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (esDocente) "Mis Archivos" else "Archivos de Divulgación",
                    color = if (pestanaSeleccionada == 1) Color.White else Color(0xFF757575),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodySmall.copy(
                        //fontSize = 11.sp,
                        lineHeight = 20.sp
                    ),
                    textAlign = TextAlign.Center,
                    softWrap = true,
                    maxLines = 2
                )
            }
            Box(
                modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(20.dp))
                .background(if (pestanaSeleccionada == 2) Color(0xFF4CAF50) else Color.Transparent)
                .clickable { pestanaSeleccionada = 2 }
                .padding(vertical = 10.dp, horizontal = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Archivos de Descarga",
                    color = if (pestanaSeleccionada == 2) Color.White else Color(0xFF757575),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 13.sp,
                        lineHeight = 12.sp
                    ),
                    textAlign = TextAlign.Center,
                    softWrap = true
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (!esDocente && pestanaSeleccionada == 0) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = codigoAcceso,
                    onValueChange = { codigoAcceso = it },
                    modifier = Modifier.weight(1f),
                    label = { Text("Código de acceso") },
                    placeholder = { Text("Ingresa el código") },
                    singleLine = true,
                    shape = RoundedCornerShape(24.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color(0xFF4CAF50),
                        focusedBorderColor = Color(0xFF2E7D32),
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White
                    )
                )
                Button(
                    onClick = {
                        if (codigoAcceso.isNotBlank()) {
                            viewModel.unirseAEspacio(codigoAcceso) { ok, mensaje ->
                                Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show()
                                if (ok) {
                                    codigoAcceso = ""
                                }
                            }
                        } else {
                            Toast.makeText(context, "Ingresa un código de acceso.", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier.height(48.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = ColorVerdeMedio)
                ) {
                    Text("Unirse", color = Color.White)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        if (pestanaSeleccionada == 1) {
            if (esDocente) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = { mostrarModalEspacios = true },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = ColorVerdeMedio)
                    ) {
                        Icon(
                            Icons.Default.School,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Espacios de Aprendizaje",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp,
                            lineHeight = 12.sp,
                            textAlign = TextAlign.Center,
                            softWrap = true,
                        )
                    }
                    Button(
                        onClick = { navController.navigate(Rutas.ArchivoCrearDivulgacion.ruta) },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = ColorVerdeMedio)
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Crear Archivos", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                }
            } else {
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
                    val esAutor = usuarioActivo?.uuidSesion == archivo.idUsuarioAutor
                    val espacioBloqueado = archivo.espacioId != null && archivo.espacioId !in misEspaciosIds
                    if (pestanaSeleccionada == 1) {
                        ItemArchivoDivulgacion(
                            archivo = archivo,
                            usuarioActual = usuarioActivo,
                            espacioBloqueado = espacioBloqueado,
                            onAbrir = {
                                if (esDocente && esAutor) {
                                    archivoParaEstadisticas = archivo
                                } else {
                                    navController.navigate(Rutas.ArchivoDetalle.conId(archivo.idArchivo))
                                }
                            },
                            onDescargar = {
                                viewModel.descargarArchivo(archivo.idArchivo) { exito ->
                                    if (exito) {
                                        Toast.makeText(context, "Archivo descargado", Toast.LENGTH_SHORT).show()
                                    } else {
                                        Toast.makeText(context, "Error al descargar", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            },
                            onEditar = if (esAutor) {
                                {
                                    navController.navigate(Rutas.ArchivoEditarDivulgacion.conId(archivo.idArchivo))
                                }
                            } else null,
                            onEliminar = if (esAutor) {
                                { viewModel.eliminarArchivoDivulgacion(archivo.idArchivo) { } }
                            } else null
                        )
                    } else {
                        ItemArchivoMockup(
                            archivo = archivo,
                            usuarioActual = usuarioActivo,
                            espacioBloqueado = espacioBloqueado,
                            onDescargar = {
                                viewModel.descargarArchivo(archivo.idArchivo) { exito ->
                                    if (exito) {
                                        Toast.makeText(context, "Archivo descargado", Toast.LENGTH_SHORT).show()
                                    } else {
                                        Toast.makeText(context, "Error al descargar", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        ) {
                            navController.navigate(Rutas.ArchivoDetalle.conId(archivo.idArchivo))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ModalEspaciosAprendizaje(
    espacios: List<EspacioAprendizaje>,
    archivos: List<Archivo>,
    usuarioId: String?,
    onActualizarArchivoEspacio: (String, String?) -> Unit,
    onDismiss: () -> Unit,
    onCrearEspacio: (String) -> Unit
) {
    var nombreEspacio by remember { mutableStateOf("") }
    var mostrandoCreacion by remember { mutableStateOf(espacios.isEmpty()) }
    var espacioSeleccionadoId by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(espacios) {
        if (espacios.isNotEmpty()) {
            mostrandoCreacion = false
        }
    }

    val espacioSeleccionado = espacios.find { it.id == espacioSeleccionadoId }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.92f),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (mostrandoCreacion) "Nuevo Espacio" else "Mis Espacios",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = ColorVerdeMedio
                )
                IconButton(onClick = { mostrandoCreacion = !mostrandoCreacion }) {
                    Icon(
                        if (mostrandoCreacion) Icons.Default.Search else Icons.Default.Add,
                        contentDescription = null,
                        tint = ColorVerdeMedio
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (mostrandoCreacion) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "El código de acceso se generará automáticamente.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(24.dp))

                    OutlinedTextField(
                        value = nombreEspacio,
                        onValueChange = { nombreEspacio = it },
                        label = { Text("Nombre del espacio") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = ColorVerdeMedio,
                            focusedLabelColor = ColorVerdeMedio
                        )
                    )
                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        TextButton(
                            onClick = { if (espacios.isNotEmpty()) mostrandoCreacion = false else onDismiss() },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Cancelar", color = Color.Gray)
                        }
                        Button(
                            onClick = {
                                if (nombreEspacio.isNotBlank()) {
                                    onCrearEspacio(nombreEspacio)
                                    nombreEspacio = ""
                                    mostrandoCreacion = false
                                }
                            },
                            modifier = Modifier.weight(1f),
                            enabled = nombreEspacio.isNotBlank(),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = ColorVerdeMedio)
                        ) {
                            Text("Crear", color = Color.White)
                        }
                    }
                }
            } else {
                Column(modifier = Modifier.weight(1f)) {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(espacios) { espacio ->
                            val seleccionado = espacio.id == espacioSeleccionadoId
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { espacioSeleccionadoId = espacio.id },
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = if (seleccionado) Color(0xFFE8F5E9) else Color(0xFFF5F5F5)
                                ),
                                border = if (seleccionado) BorderStroke(1.dp, ColorVerdeMedio) else null
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = espacio.nombre,
                                            style = MaterialTheme.typography.bodyLarge,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.DarkGray
                                        )
                                        Text(
                                            text = "Código: ${espacio.codigoAcceso}",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = ColorVerdeMedio
                                        )
                                    }
                                    if (seleccionado) {
                                        Text(
                                            text = "Seleccionado",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = ColorVerdeMedio,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        }
                    }

                    if (espacioSeleccionado != null) {
                        val archivosVisibles = archivos.filter { it.espacioId == null || it.espacioId == espacioSeleccionado.id }

                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Archivos para ${espacioSeleccionado.nombre}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = ColorVerdeMedio
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        if (archivosVisibles.isEmpty()) {
                            Text(
                                text = "No tienes archivos disponibles para asignar.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray
                            )
                        } else {
                            LazyColumn(
                                modifier = Modifier.fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(archivosVisibles) { archivo ->
                                    val asignado = archivo.espacioId == espacioSeleccionado.id
                                    Card(
                                        modifier = Modifier.fillMaxWidth(),
                                        shape = RoundedCornerShape(12.dp),
                                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F9F9))
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(12.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Column(modifier = Modifier.weight(1f)) {
                                                Text(
                                                    text = archivo.titulo,
                                                    style = MaterialTheme.typography.bodyMedium,
                                                    fontWeight = FontWeight.SemiBold,
                                                    color = Color.DarkGray
                                                )
                                                Text(
                                                    text = if (archivo.espacioId == null) "Sin espacio asignado" else if (asignado) "Asignado a este espacio" else "Asignado a otro espacio",
                                                    style = MaterialTheme.typography.bodySmall,
                                                    color = if (asignado) Color(0xFF388E3C) else Color.Gray
                                                )
                                            }
                                            Button(
                                                onClick = {
                                                    val nuevoEspacioId = if (asignado) null else espacioSeleccionado.id
                                                    onActualizarArchivoEspacio(archivo.idArchivo, nuevoEspacioId)
                                                },
                                                shape = RoundedCornerShape(12.dp),
                                                colors = ButtonDefaults.buttonColors(
                                                    containerColor = if (asignado) Color.Gray else ColorVerdeMedio
                                                )
                                            ) {
                                                Text(
                                                    text = if (asignado) "Quitar" else "Asignar",
                                                    color = Color.White
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
                ) {
                    Text("Cerrar")
                }
            }
        }
    }
}

@Composable
fun ModalEstadisticasArchivo(
    archivo: Archivo,
    estadisticas: EstadisticasArchivo?,
    onDismiss: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.8f),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = archivo.titulo,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = ColorVerdeMedio,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (estadisticas == null) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = ColorVerdeMedio)
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .height(100.dp),
                        shape = RoundedCornerShape(16.dp),
                        color = ColorVerdeClaro
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "${estadisticas.promedioGeneral}",
                                    style = MaterialTheme.typography.headlineLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = ColorVerdeMedio
                                )
                                Text(
                                    text = "Promedio General",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = ColorVerdeMedio
                                )
                            }
                        }
                    }

                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .height(100.dp),
                        shape = RoundedCornerShape(16.dp),
                        color = ColorVerdeMedio
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "${estadisticas.totalIntentos}",
                                    style = MaterialTheme.typography.headlineLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Text(
                                    text = "# Total de Intentos en ${archivo.titulo}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color.White,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Promedio por estudiante",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = ColorVerdeMedio
                )
                Spacer(modifier = Modifier.height(12.dp))

                if (estadisticas.detallesEstudiantes.isEmpty()) {
                    Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                        Text("No hay intentos registrados aún.", color = Color.Gray)
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(estadisticas.detallesEstudiantes.size) { index ->
                            val item = estadisticas.detallesEstudiantes[index]
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = if (index % 2 == 0) Color(0xFFF5F5F5) else Color.White
                                )
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp, vertical = 12.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = item.nombreEstudiante,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Color.DarkGray
                                    )
                                    Text(
                                        text = "${item.calificacion}%",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = if (item.calificacion >= 70) ColorVerdeMedio else Color(0xFFE53935)
                                    )
                                }
                            }
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
    espacioBloqueado: Boolean,
    onDescargar: () -> Unit,
    onClick: () -> Unit
) {
    val context = LocalContext.current
    val nivelArchivo = archivo.nivelRequerido
    val nivelUsuario = usuarioActual?.nivel?.jerarquia ?: 1
    val bloqueado = espacioBloqueado || nivelUsuario < nivelArchivo
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
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Top
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
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

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "Descripción: ${archivo.descripcion}",
                                style = MaterialTheme.typography.bodySmall,
                                color = if (bloqueado) Color(0xFFBDBDBD) else Color.DarkGray,
                                maxLines = 4,
                                overflow = TextOverflow.Ellipsis
                            )
                        }

                        if (!archivo.autorOriginal.isNullOrBlank() || !archivo.licencia.isNullOrBlank()) {
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(
                                modifier = Modifier
                                    .width(140.dp)
                                    .padding(top = 4.dp),
                                verticalArrangement = Arrangement.Top
                            ) {
                                archivo.autorOriginal?.let {
                                    Text(
                                        text = "Autor original",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = Color(0xFF616161),
                                        fontWeight = FontWeight.Medium
                                    )
                                    Text(
                                        text = it,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = if (bloqueado) Color(0xFFBDBDBD) else Color.DarkGray,
                                        maxLines = 2,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                }
                                archivo.licencia?.let {
                                    Text(
                                        text = "Licencia",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = Color(0xFF616161),
                                        fontWeight = FontWeight.Medium
                                    )
                                    Text(
                                        text = it,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = if (bloqueado) Color(0xFFBDBDBD) else Color.DarkGray,
                                        maxLines = 2,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }
                        }
                    }
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
                        onDescargar()
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
    espacioBloqueado: Boolean,
    onAbrir: () -> Unit,
    onDescargar: () -> Unit,
    onEditar: (() -> Unit)?,
    onEliminar: (() -> Unit)?
) {
    val context = LocalContext.current
    val nivelArchivo = archivo.nivelRequerido
    val nivelUsuario = usuarioActual?.nivel?.jerarquia ?: 1
    val esAutor = usuarioActual?.uuidSesion == archivo.idUsuarioAutor
    val bloqueado = espacioBloqueado || (nivelUsuario < nivelArchivo && !esAutor)
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
                        onDescargar()
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
