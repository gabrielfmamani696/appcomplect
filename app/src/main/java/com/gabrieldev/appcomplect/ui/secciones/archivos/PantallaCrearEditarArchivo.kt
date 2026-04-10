package com.gabrieldev.appcomplect.ui.secciones.archivos

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.speech.RecognizerIntent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.gabrieldev.appcomplect.model.BorradorRespuestaDivulgacion
import com.gabrieldev.appcomplect.model.BorradorTarjetaDivulgacion
import com.gabrieldev.appcomplect.ui.theme.ColorFondo
import com.gabrieldev.appcomplect.ui.theme.ColorVerde
import com.gabrieldev.appcomplect.ui.theme.ColorVerdeClaro
import com.gabrieldev.appcomplect.ui.theme.ColorVerdeMedio

@Composable
fun OutlinedTextFieldConAudio(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    minLines: Int = 1,
    singleLine: Boolean = false
) {

    val recognizerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val results = result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            if (!results.isNullOrEmpty()) {
                val recognizedText = results[0]
                onValueChange(if (value.isBlank()) recognizedText else "$value $recognizedText")
            }
        }
    }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = modifier,
        minLines = minLines,
        singleLine = singleLine,
        trailingIcon = {
            IconButton(onClick = {
                val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                    putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                    putExtra(RecognizerIntent.EXTRA_LANGUAGE, "es-ES")
                    putExtra(RecognizerIntent.EXTRA_PROMPT, "Habla para llenar el campo...")
                }
                recognizerLauncher.launch(intent)
            }) {
                Icon(Icons.Default.Mic, contentDescription = "Grabar audio", tint = ColorVerdeMedio)
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = ColorVerdeMedio,
            unfocusedBorderColor = ColorVerdeMedio.copy(alpha = 0.6f)
        )
    )
}

@Composable
fun PantallaCrearEditarArchivo(
    viewModel: CrearArchivoViewModel,
    onNavigateBack: () -> Unit
) {
    val titulo by viewModel.titulo.collectAsState()
    val tema by viewModel.tema.collectAsState()
    val descripcion by viewModel.descripcion.collectAsState()
    val imagenPortada by viewModel.imagenPortada.collectAsState()
    val listaCuestionarios by viewModel.listaCuestionarios.collectAsState()
    val cuestionarioActivoId by viewModel.cuestionarioActivoId.collectAsState()
    val quiereCuestionario by viewModel.quiereCuestionario.collectAsState()
    val tarjetas by viewModel.tarjetas.collectAsState()
    val mensajeUsuario by viewModel.mensajeUsuario.collectAsState()
    val navegarAtras by viewModel.navegarAtras.collectAsState()
    val cargando by viewModel.cargando.collectAsState()

    var pasoActual by remember { mutableIntStateOf(1) }
    val totalPasos = 3
    val snackbarHostState = remember { SnackbarHostState() }
    val esEdicion = viewModel.idArchivoEditar != null
    var mostrarConfirmacionDescartar by remember { mutableStateOf(false) }

    var tarjetaSiendoEditada by remember { mutableStateOf<Int?>(null) }
    var cuestionarioSiendoEditado by remember { mutableStateOf<String?>(null) }
    var preguntaSiendoEditada by remember { mutableStateOf<Int?>(null) }

    LaunchedEffect(mensajeUsuario) {
        mensajeUsuario?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.limpiarMensajes()
        }
    }
    LaunchedEffect(navegarAtras) {
        if (navegarAtras) onNavigateBack()
    }

    if (cargando && esEdicion && tarjetas.isEmpty() && titulo.isEmpty()) {
        Box(Modifier.fillMaxSize().background(ColorFondo), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = ColorVerdeMedio)
        }
        return
    }

    if (mostrarConfirmacionDescartar) {
        AlertDialog(
            onDismissRequest = { mostrarConfirmacionDescartar = false },
            title = { Text("¿Descartar cambios?") },
            text = { Text("Todo el trabajo realizado hasta ahora será eliminado. ¿Estás seguro?") },
            confirmButton = {
                Button(
                    onClick = { 
                        mostrarConfirmacionDescartar = false
                        onNavigateBack() 
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC62828))
                ) {
                    Text("Eliminar", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarConfirmacionDescartar = false }) {
                    Text("Continuar editando", color = ColorVerde)
                }
            }
        )
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = ColorFondo,
        topBar = {
            Surface(shadowElevation = 4.dp, color = ColorVerde) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { mostrarConfirmacionDescartar = true }) {
                        Icon(Icons.Default.Close, contentDescription = "Cerrar", tint = Color.White)
                    }
                    Spacer(Modifier.width(8.dp))
                    Column(Modifier.weight(1f)) {
                        Text(
                            text = if (esEdicion) "Editar publicación" else "Crear publicación",
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.White
                        )
                        Text(
                            text = "Paso $pasoActual de $totalPasos",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFFC8E6C9)
                        )
                    }

                    IconButton(onClick = { mostrarConfirmacionDescartar = true }) {
                        Icon(Icons.Default.Delete, contentDescription = "Descartar cambios", tint = Color.White)
                    }
                }
            }
        },
        bottomBar = {
            BottomAppBar(containerColor = Color.White) {
                if (pasoActual > 1) {
                    TextButton(onClick = { pasoActual-- }) {
                        Text("Anterior", color = ColorVerde)
                    }
                } else {
                    Spacer(Modifier.width(8.dp))
                }
                Spacer(Modifier.weight(1f))
                if (pasoActual < totalPasos) {
                    Button(
                        onClick = {
                            if (pasoActual == 2 && tarjetas.isEmpty()) {
                                viewModel.actualizarMensaje("Debes añadir al menos una tarjeta.")
                            } else {
                                pasoActual++
                            }
                        },
                        enabled = if (pasoActual == 1) titulo.isNotBlank() else true,
                        colors = ButtonDefaults.buttonColors(containerColor = ColorVerdeMedio)
                    ) {
                        Text("Siguiente", color = Color.White)
                    }
                } else {
                    Button(
                        onClick = { viewModel.guardarTodo() },
                        enabled = !cargando,
                        colors = ButtonDefaults.buttonColors(containerColor = ColorVerde)
                    ) {
                        if (cargando) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(22.dp),
                                color = Color.White,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text("Guardar", color = Color.White)
                        }
                    }
                }
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            when (pasoActual) {
                1 -> PasoDatosDivulgacion(
                    titulo = titulo,
                    onTituloChange = viewModel::actualizarTitulo,
                    tema = tema,
                    onTemaChange = viewModel::actualizarTema,
                    descripcion = descripcion,
                    onDescripcionChange = viewModel::actualizarDescripcion,
                    imagenPortada = imagenPortada,
                    onImagenPortadaChange = viewModel::actualizarImagenPortada
                )
                2 -> PasoTarjetasDivulgacion(
                    listaTarjetas = tarjetas,
                    onAgregar = { c, tipo, data -> 
                        if (tarjetaSiendoEditada != null) {
                            viewModel.eliminarTarjeta(tarjetaSiendoEditada!!)
                            tarjetaSiendoEditada = null
                        }
                        viewModel.agregarTarjeta(c, tipo, data) 
                    },
                    onEliminar = viewModel::eliminarTarjeta,
                    onEditar = { index ->
                        tarjetaSiendoEditada = index
                    },
                    onCancelarEdicion = { tarjetaSiendoEditada = null },
                    tarjetaSiendoEditada = tarjetaSiendoEditada
                )
                3 -> PasoCuestionariosDivulgacion(
                    listaCuestionarios = listaCuestionarios,
                    cuestionarioActivoId = cuestionarioActivoId,
                    quiereCuestionario = quiereCuestionario,
                    esEstudiante = viewModel.esEstudiante,
                    onSetQuiereCuestionario = viewModel::setQuiereCuestionario,
                    onCrearCuestionario = viewModel::crearNuevoCuestionario,
                    onSeleccionarCuestionario = viewModel::seleccionarCuestionario,
                    onEliminarCuestionario = viewModel::eliminarCuestionario,
                    onAgregarPregunta = { e, r -> 
                        if (preguntaSiendoEditada != null) {
                            viewModel.eliminarPregunta(preguntaSiendoEditada!!)
                            preguntaSiendoEditada = null
                        }
                        viewModel.agregarPregunta(e, r) 
                    },
                    onEliminarPregunta = viewModel::eliminarPregunta,
                    onTituloChange = viewModel::actualizarTituloCuestionario,
                    onEditarCuestionario = { id -> cuestionarioSiendoEditado = id },
                    onEditarPregunta = { index -> preguntaSiendoEditada = index },
                    onCancelarEdicionPregunta = { preguntaSiendoEditada = null },
                    preguntaSiendoEditada = preguntaSiendoEditada
                )
            }
        }
    }
}

@Composable
private fun PasoDatosDivulgacion(
    titulo: String,
    onTituloChange: (String) -> Unit,
    tema: String,
    onTemaChange: (String) -> Unit,
    descripcion: String,
    onDescripcionChange: (String) -> Unit,
    imagenPortada: String?,
    onImagenPortadaChange: (String?) -> Unit
) {
    val launcherPortada = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        onImagenPortadaChange(uri?.toString())
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        Text("Información general", style = MaterialTheme.typography.headlineSmall, color = ColorVerde)
        Spacer(Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(ColorVerdeClaro)
                .clickable {
                    launcherPortada.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                },
            contentAlignment = Alignment.Center
        ) {
            if (imagenPortada != null) {
                AsyncImage(
                    model = imagenPortada,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                IconButton(
                    onClick = { onImagenPortadaChange(null) },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .background(Color.Black.copy(alpha = 0.5f), CircleShape)
                ) {
                    Icon(Icons.Default.Close, null, tint = Color.White)
                }
            } else {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Default.Image,
                        null,
                        modifier = Modifier.size(50.dp),
                        tint = ColorVerde
                    )
                    Text("Portada (opcional)", color = ColorVerde)
                }
            }
        }

        Spacer(Modifier.height(16.dp))
        OutlinedTextFieldConAudio(
            value = titulo,
            onValueChange = onTituloChange,
            label = "Título",
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextFieldConAudio(
            value = tema,
            onValueChange = onTemaChange,
            label = "Tema",
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextFieldConAudio(
            value = descripcion,
            onValueChange = onDescripcionChange,
            label = "Descripción",
            modifier = Modifier.fillMaxWidth(),
            minLines = 2
        )
    }
}

@Composable
private fun PasoTarjetasDivulgacion(
    listaTarjetas: List<BorradorTarjetaDivulgacion>,
    onAgregar: (String, String, String) -> Unit,
    onEliminar: (Int) -> Unit,
    onEditar: (Int) -> Unit,
    onCancelarEdicion: () -> Unit,
    tarjetaSiendoEditada: Int?
) {
    var contenido by remember { mutableStateOf("") }
    var imagenUri by remember { mutableStateOf<android.net.Uri?>(null) }

    val colores = listOf(
        "#FFB3BA" to Color(0xFFFFB3BA),
        "#FFDFBA" to Color(0xFFFFDFBA),
        "#FFFFBA" to Color(0xFFFFFFBA),
        "#BAFFC9" to Color(0xFFBAFFC9),
        "#BAE1FF" to Color(0xFFBAE1FF),
        "#E1BAFF" to Color(0xFFE1BAFF)
    )
    var colorSeleccionado by remember { mutableStateOf(colores[3].first) }
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { imagenUri = it }

    LaunchedEffect(tarjetaSiendoEditada) {
        tarjetaSiendoEditada?.let { index ->
            val t = listaTarjetas[index]
            contenido = t.contenidoTexto
            if (t.tipoFondo == "imagen") {
                imagenUri = Uri.parse(t.dataFondo)
            } else {
                colorSeleccionado = t.dataFondo
                imagenUri = null
            }
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        item {
            Text("Tarjetas", style = MaterialTheme.typography.headlineSmall, color = ColorVerde)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .then(
                        if (tarjetaSiendoEditada != null) {
                            Modifier
                                .shadow(8.dp, RoundedCornerShape(12.dp), spotColor = ColorVerdeMedio)
                                .border(2.dp, ColorVerdeMedio, RoundedCornerShape(12.dp))
                        } else Modifier
                    ),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(if (tarjetaSiendoEditada != null) 8.dp else 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        if (tarjetaSiendoEditada != null) "Editando tarjeta" else "Nueva tarjeta",
                        color = ColorVerde,
                        style = MaterialTheme.typography.labelLarge
                    )
                    Spacer(Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(ColorVerdeClaro)
                            .clickable {
                                launcher.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        if (imagenUri != null) {
                            AsyncImage(
                                model = imagenUri,
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                            IconButton(
                                onClick = { imagenUri = null },
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .background(Color.Black.copy(alpha = 0.5f), CircleShape)
                            ) {
                                Icon(Icons.Default.Close, null, tint = Color.White)
                            }
                        } else {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(Icons.Default.Image, null, Modifier.size(48.dp), tint = ColorVerde)
                                Text("Imagen de tarjeta", color = ColorVerde)
                            }
                        }
                    }
                    Spacer(Modifier.height(12.dp))
                    if (imagenUri == null) {
                        Text("Color de fondo", color = ColorVerde, style = MaterialTheme.typography.bodySmall)
                        Row(Modifier.fillMaxWidth().padding(vertical = 8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            colores.forEach { (hex, color) ->
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                        .background(color)
                                        .clickable { colorSeleccionado = hex }
                                        .then(
                                            if (colorSeleccionado == hex)
                                                Modifier.border(2.dp, ColorVerde, CircleShape)
                                            else Modifier
                                        )
                                )
                            }
                        }
                    }
                    OutlinedTextFieldConAudio(
                        value = contenido,
                        onValueChange = { contenido = it },
                        label = "Texto",
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 2
                    )
                    Spacer(Modifier.height(8.dp))
                    Row(modifier = Modifier.align(Alignment.End)) {
                        if (tarjetaSiendoEditada != null) {
                            TextButton(onClick = { 
                                onCancelarEdicion()
                                contenido = ""
                                imagenUri = null
                            }) {
                                Text("Cancelar", color = Color.Gray)
                            }
                            Spacer(Modifier.width(8.dp))
                        }
                        Button(
                            onClick = {
                                if (contenido.isNotBlank()) {
                                    val tipo = if (imagenUri != null) "imagen" else "color"
                                    val data = if (imagenUri != null) imagenUri.toString() else colorSeleccionado
                                    onAgregar(contenido, tipo, data)
                                    contenido = ""
                                    imagenUri = null
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = ColorVerdeMedio)
                        ) {
                            Icon(if (tarjetaSiendoEditada != null) Icons.Default.Edit else Icons.Default.Add, null, tint = Color.White)
                            Spacer(Modifier.width(4.dp))
                            Text(if (tarjetaSiendoEditada != null) "Actualizar" else "Añadir", color = Color.White)
                        }
                    }
                }
            }
            HorizontalDivider(color = ColorVerdeClaro)
            Text("Creadas (${listaTarjetas.size})", color = ColorVerde, modifier = Modifier.padding(vertical = 8.dp))
        }
        itemsIndexed(listaTarjetas) { index, tarjeta ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Row(
                    modifier = Modifier.padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (tarjeta.tipoFondo.equals("imagen", true)) {
                        AsyncImage(
                            model = tarjeta.dataFondo,
                            contentDescription = null,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(4.dp)),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        val c = try {
                            Color(android.graphics.Color.parseColor(tarjeta.dataFondo))
                        } catch (_: Exception) {
                            ColorVerdeClaro
                        }
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(c)
                        )
                    }
                    Spacer(Modifier.width(8.dp))
                    Text(tarjeta.contenidoTexto, maxLines = 2, modifier = Modifier.weight(1f))
                    IconButton(onClick = { onEditar(index) }) {
                        Icon(Icons.Default.Edit, null, tint = ColorVerde)
                    }
                    IconButton(onClick = { onEliminar(index) }) {
                        Icon(Icons.Default.Delete, null, tint = Color(0xFFC62828))
                    }
                }
            }
        }
    }
}

@Composable
private fun PasoCuestionariosDivulgacion(
    listaCuestionarios: List<CuestionarioBorradorDivulgacion>,
    cuestionarioActivoId: String?,
    quiereCuestionario: Boolean?,
    esEstudiante: Boolean,
    onSetQuiereCuestionario: (Boolean) -> Unit,
    onCrearCuestionario: (String) -> Unit,
    onSeleccionarCuestionario: (String?) -> Unit,
    onEliminarCuestionario: (String) -> Unit,
    onAgregarPregunta: (String, List<BorradorRespuestaDivulgacion>) -> Unit,
    onEliminarPregunta: (Int) -> Unit,
    onTituloChange: (String) -> Unit,
    onEditarCuestionario: (String) -> Unit,
    onEditarPregunta: (Int) -> Unit,
    onCancelarEdicionPregunta: () -> Unit,
    preguntaSiendoEditada: Int?
) {
    if (esEstudiante && quiereCuestionario == null) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "¿Quieres construir un cuestionario para esta publicación?",
                style = MaterialTheme.typography.headlineSmall,
                color = ColorVerde,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(24.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Button(
                    onClick = { onSetQuiereCuestionario(true) },
                    colors = ButtonDefaults.buttonColors(containerColor = ColorVerdeMedio)
                ) {
                    Text("Sí, crear cuestionario", color = Color.White)
                }
                OutlinedButton(
                    onClick = { onSetQuiereCuestionario(false) },
                    border = BorderStroke(1.dp, ColorVerdeMedio)
                ) {
                    Text("No, solo tarjetas", color = ColorVerdeMedio)
                }
            }
        }
        return
    }

    if (cuestionarioActivoId == null) {
        VistaListaCuestionariosDivulgacion(
            lista = listaCuestionarios,
            onCrear = onCrearCuestionario,
            onSeleccionar = onSeleccionarCuestionario,
            onEliminar = onEliminarCuestionario,
            onEditar = onEditarCuestionario
        )
    } else {
        val activo = listaCuestionarios.find { it.idTemporal == cuestionarioActivoId }
        if (activo != null) {
            VistaEditorPreguntasDivulgacion(
                cuestionario = activo,
                onVolver = { onSeleccionarCuestionario(null) },
                onAgregar = onAgregarPregunta,
                onEliminar = onEliminarPregunta,
                onTituloChange = onTituloChange,
                onEditar = onEditarPregunta,
                onCancelarEdicion = onCancelarEdicionPregunta,
                preguntaSiendoEditada = preguntaSiendoEditada
            )
        } else {
            LaunchedEffect(Unit) { onSeleccionarCuestionario(null) }
        }
    }
}

@Composable
private fun VistaListaCuestionariosDivulgacion(
    lista: List<CuestionarioBorradorDivulgacion>,
    onCrear: (String) -> Unit,
    onSeleccionar: (String) -> Unit,
    onEliminar: (String) -> Unit,
    onEditar: (String) -> Unit
) {
    var mostrarDialogo by remember { mutableStateOf(false) }
    var nuevoTitulo by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Cuestionario (opcional)", style = MaterialTheme.typography.headlineSmall, color = ColorVerde)
            Button(
                onClick = { mostrarDialogo = true },
                colors = ButtonDefaults.buttonColors(containerColor = ColorVerdeMedio)
            ) {
                Icon(Icons.Default.Add, null, tint = Color.White)
                Text(" Nuevo", color = Color.White)
            }
        }
        Spacer(Modifier.height(16.dp))
        if (lista.isEmpty()) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        "Sin cuestionario. Los lectores solo verán las tarjetas.",
                        color = Color(0xFF757575),
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(16.dp))
                    Button(
                        onClick = { },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
                    ) {
                        Text("Cerrar", color = Color.White)
                    }
                }
            }
        } else {
            LazyColumn(modifier = Modifier.weight(1f)) {
                itemsIndexed(lista) { _, item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable { onSeleccionar(item.idTemporal) },
                        colors = CardDefaults.cardColors(containerColor = ColorVerdeClaro)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(item.titulo, style = MaterialTheme.typography.titleMedium, color = ColorVerde)
                                Text("${item.preguntas.size} preguntas", style = MaterialTheme.typography.bodySmall)
                            }
                            IconButton(onClick = { onEliminar(item.idTemporal) }) {
                                Icon(Icons.Default.Delete, null, tint = Color(0xFFC62828))
                            }
                        }
                    }
                }
            }
        }
    }

    if (mostrarDialogo) {
        AlertDialog(
            onDismissRequest = { mostrarDialogo = false },
            title = { Text("Nuevo cuestionario", color = ColorVerde) },
            text = {
                OutlinedTextFieldConAudio(
                    value = nuevoTitulo,
                    onValueChange = { nuevoTitulo = it },
                    label = "Título",
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (nuevoTitulo.isNotBlank()) {
                            onCrear(nuevoTitulo)
                            nuevoTitulo = ""
                            mostrarDialogo = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = ColorVerdeMedio)
                ) { Text("Crear", color = Color.White) }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDialogo = false }) {
                    Text("Cancelar", color = ColorVerde)
                }
            }
        )
    }
}

@Composable
private fun VistaEditorPreguntasDivulgacion(
    cuestionario: CuestionarioBorradorDivulgacion,
    onVolver: () -> Unit,
    onAgregar: (String, List<BorradorRespuestaDivulgacion>) -> Unit,
    onEliminar: (Int) -> Unit,
    onTituloChange: (String) -> Unit,
    onEditar: (Int) -> Unit,
    onCancelarEdicion: () -> Unit,
    preguntaSiendoEditada: Int?
) {
    var mostrarDialogo by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onVolver) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = ColorVerde)
            }
            OutlinedTextFieldConAudio(
                value = cuestionario.titulo,
                onValueChange = onTituloChange,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp),
                label = "Título del cuestionario",
                singleLine = true
            )
        }
        HorizontalDivider(color = ColorVerdeClaro)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("${cuestionario.preguntas.size} preguntas", color = ColorVerde)
            Button(
                onClick = { mostrarDialogo = true },
                colors = ButtonDefaults.buttonColors(containerColor = ColorVerdeMedio)
            ) {
                Icon(Icons.Default.Add, null, tint = Color.White)
                Text(" Pregunta", color = Color.White)
            }
        }
        LazyColumn(modifier = Modifier.weight(1f)) {
            itemsIndexed(cuestionario.preguntas) { index, paquete ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .then(
                            if (preguntaSiendoEditada == index) {
                                Modifier
                                    .shadow(8.dp, RoundedCornerShape(12.dp), spotColor = ColorVerdeMedio)
                                    .border(2.dp, ColorVerdeMedio, RoundedCornerShape(12.dp))
                            } else Modifier
                        ),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(if (preguntaSiendoEditada == index) 8.dp else 2.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("P${index + 1}", color = ColorVerde, style = MaterialTheme.typography.titleMedium)
                            Spacer(Modifier.width(8.dp))
                            Text(
                                paquete.enunciado,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.weight(1f)
                            )
                            IconButton(onClick = { onEditar(index) }) {
                                Icon(Icons.Default.Edit, null, tint = ColorVerde)
                            }
                            IconButton(onClick = { onEliminar(index) }) {
                                Icon(Icons.Default.Delete, null, tint = Color(0xFFC62828))
                            }
                        }
                        paquete.respuestas.forEach { resp ->
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    if (resp.esCorrecta) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                                    null,
                                    Modifier.size(16.dp),
                                    tint = if (resp.esCorrecta) ColorVerdeMedio else Color.Gray
                                )
                                Spacer(Modifier.width(4.dp))
                                Text(resp.textoOpcion, style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                }
            }
        }
    }

    if (mostrarDialogo || preguntaSiendoEditada != null) {
        DialogoCrearPreguntaDivulgacion(
            preguntaAEditar = if (preguntaSiendoEditada != null) cuestionario.preguntas[preguntaSiendoEditada] else null,
            onDismiss = { 
                mostrarDialogo = false 
                onCancelarEdicion()
            },
            onConfirm = { e, r ->
                onAgregar(e, r)
                mostrarDialogo = false
            }
        )
    }
}

@Composable
private fun DialogoCrearPreguntaDivulgacion(
    preguntaAEditar: PreguntaBorradorDivulgacion? = null,
    onDismiss: () -> Unit,
    onConfirm: (String, List<BorradorRespuestaDivulgacion>) -> Unit
) {
    var enunciado by remember { mutableStateOf(preguntaAEditar?.enunciado ?: "") }
    var op1 by remember { mutableStateOf(preguntaAEditar?.respuestas?.getOrNull(0)?.textoOpcion ?: "") }
    var op2 by remember { mutableStateOf(preguntaAEditar?.respuestas?.getOrNull(1)?.textoOpcion ?: "") }
    var op3 by remember { mutableStateOf(preguntaAEditar?.respuestas?.getOrNull(2)?.textoOpcion ?: "") }
    var correctaIndex by remember { 
        mutableIntStateOf(preguntaAEditar?.respuestas?.indexOfFirst { it.esCorrecta }?.coerceAtLeast(0) ?: 0) 
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (preguntaAEditar != null) "Editar pregunta" else "Nueva pregunta", color = ColorVerde) },
        text = {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                OutlinedTextFieldConAudio(
                    value = enunciado,
                    onValueChange = { enunciado = it },
                    label = "Enunciado",
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
                Text("Opciones (marca la correcta)", color = ColorVerde)
                listOf(0, 1, 2).forEach { index ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = correctaIndex == index,
                            onClick = { correctaIndex = index }
                        )
                        OutlinedTextFieldConAudio(
                            value = when (index) {
                                0 -> op1
                                1 -> op2
                                else -> op3
                            },
                            onValueChange = {
                                when (index) {
                                    0 -> op1 = it
                                    1 -> op2 = it
                                    else -> op3 = it
                                }
                            },
                            label = "Opción ${index + 1}",
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (enunciado.isNotBlank() && op1.isNotBlank() && op2.isNotBlank() && op3.isNotBlank()) {
                        val respuestas = listOf(
                            BorradorRespuestaDivulgacion(op1, correctaIndex == 0),
                            BorradorRespuestaDivulgacion(op2, correctaIndex == 1),
                            BorradorRespuestaDivulgacion(op3, correctaIndex == 2)
                        )
                        onConfirm(enunciado, respuestas)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = ColorVerdeMedio)
            ) { Text(if (preguntaAEditar != null) "Actualizar" else "Añadir", color = Color.White) }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar", color = ColorVerde) }
        }
    )
}
