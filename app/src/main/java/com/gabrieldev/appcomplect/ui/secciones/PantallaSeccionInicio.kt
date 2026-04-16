package com.gabrieldev.appcomplect.ui.secciones

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.gabrieldev.appcomplect.data.repository.UsuarioRepository
import com.gabrieldev.appcomplect.model.Nivel
import com.gabrieldev.appcomplect.model.Usuario
import com.gabrieldev.appcomplect.model.UsuarioLeaderboard
import com.gabrieldev.appcomplect.ui.MainViewModel
import com.gabrieldev.appcomplect.ui.componentes.AlertaInsigniaHost
import java.util.Locale
import java.util.Locale.getDefault

@Composable
fun PantallaSeccionInicio(
    usuario: Usuario,
    usuarioRepository: UsuarioRepository,
    mainViewModel: MainViewModel
) {
    val scrollState = rememberScrollState()
    val usuarioVivo by usuarioRepository.usuarioActivo.collectAsState()
    val usuarioActual = usuarioVivo ?: usuario

    val tipoTexto = (usuarioActual.nombreRol.ifBlank { "Investigador" }).uppercase(getDefault())
    val estrellasActuales = usuarioActual.estrellasPrestigio
    val nivelActual = usuarioActual.nivel

    var todosNiveles by remember { mutableStateOf<List<Nivel>>(emptyList()) }

    val progresoObjetivo = remember(estrellasActuales, nivelActual, todosNiveles) {
        if (nivelActual == null || todosNiveles.isEmpty()) return@remember 0f
        val sorted = todosNiveles.sortedBy { it.jerarquia }
        val idxActual = sorted.indexOfFirst { it.id == nivelActual.id }
        if (idxActual < 0) return@remember 0f
        val reqActual = sorted[idxActual].estrellasRequeridas
        val reqAnterior = if (idxActual > 0) sorted[idxActual - 1].estrellasRequeridas else 0
        val rango = reqActual - reqAnterior
        if (rango <= 0) return@remember 1f
        ((estrellasActuales - reqAnterior).toFloat() / rango.toFloat()).coerceIn(0f, 1f)
    }

    val insigniasNuevas by mainViewModel.insigniasNuevas.collectAsState()
    val insigniasObtenidas by mainViewModel.insigniasObtenidas.collectAsState()
    var mostrarMisInsignias by remember { mutableStateOf(false) }

    var progresoAnimado by remember { mutableFloatStateOf(0f) }
    val progresoState by animateFloatAsState(
        targetValue = progresoAnimado,
        animationSpec = tween(durationMillis = 1000),
        label = "progreso"
    )

    var leaderboard by remember { mutableStateOf<List<UsuarioLeaderboard>>(emptyList()) }
    var investigadoresOrdenados by remember { mutableStateOf<List<UsuarioLeaderboard>>(emptyList()) }

    val usuarioActualId = usuarioActual.uuidSesion
    val filaUsuarioActual = remember(investigadoresOrdenados, usuarioActualId) {
        if (usuarioActualId.isNullOrBlank()) return@remember null
        investigadoresOrdenados.firstOrNull { it.id == usuarioActualId }
    }
    val posicionActual = remember(investigadoresOrdenados, usuarioActualId) {
        if (usuarioActualId.isNullOrBlank()) 0
        else investigadoresOrdenados.indexOfFirst { it.id == usuarioActualId } + 1
    }

    LaunchedEffect(progresoObjetivo) {
        progresoAnimado = progresoObjetivo
    }

    LaunchedEffect(Unit) {
        todosNiveles = usuarioRepository.obtenerTodosLosNiveles()
        leaderboard = usuarioRepository.obtenerLeaderboardTop5()
        investigadoresOrdenados = usuarioRepository.obtenerInvestigadoresOrdenadosPorEstrellas()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF1F8F1))
                .verticalScroll(scrollState)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color(0xFF1B5E20), Color(0xFF388E3C))
                        )
                    )
                    .padding(top = 16.dp, bottom = 20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(56.dp)) 
                    
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.weight(1f).padding(end = 56.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF4CAF50)),
                            contentAlignment = Alignment.Center
                        ) {
                            SubcomposeAsyncImage(
                                model = usuarioActual.avatarUrl.ifBlank { usuarioActual.idAvatar },
                                contentDescription = "Avatar",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize(),
                                loading = {
                                    Icon(
                                        Icons.Default.Person,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(52.dp)
                                    )
                                },
                                error = {
                                    Icon(
                                        Icons.Default.Person,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(52.dp)
                                    )
                                }
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = if (usuarioActual.alias == "..." || usuarioActual.alias.isBlank()) "Cargando..." else "¡Hola, ${usuarioActual.alias}!",
                            style = MaterialTheme.typography.headlineSmall,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )

                        if (usuarioActual.nombre.isNotBlank()) {
                            Text(
                                text = "${usuarioActual.nombre} ${usuarioActual.apellidoPaterno}".trim(),
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFFB9F6CA)
                            )
                        }

                        Text(
                            text = tipoTexto,
                            style = MaterialTheme.typography.labelLarge,
                            color = Color(0xFF69F0AE)
                        )
                    }
                }
            }

            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Default.Star,
                                contentDescription = null,
                                tint = Color(0xFF4CAF50),
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = usuarioActual.nivel?.nombreRango?.takeIf { it.isNotBlank() } ?: "Sin nivel asignado",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color(0xFF2E7D32),
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                text = run {
                                    val sorted = todosNiveles.sortedBy { it.jerarquia }
                                    val idxActual = sorted.indexOfFirst { it.id == nivelActual?.id }
                                    if (idxActual < 0) "$estrellasActuales / —"
                                    else {
                                        val reqActual = sorted[idxActual].estrellasRequeridas
                                        val reqAnterior = if (idxActual > 0) sorted[idxActual - 1].estrellasRequeridas else 0
                                        val conseguidas = (estrellasActuales - reqAnterior).coerceAtLeast(0)
                                        val necesarias = reqActual - reqAnterior
                                        "$conseguidas / $necesarias"
                                    }
                                },
                                style = MaterialTheme.typography.labelMedium,
                                color = Color(0xFF757575)
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        LinearProgressIndicator(
                            progress = { progresoState },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(10.dp)
                                .clip(RoundedCornerShape(50)),
                            color = Color(0xFF4CAF50),
                            trackColor = Color(0xFFE8F5E9),
                            strokeCap = StrokeCap.Round,
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "${(progresoState * 100).toInt()}% completado",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFF9E9E9E)
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(110.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF2E7D32)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(text = "🔥", fontSize = 28.sp)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "${usuarioActual.rachaActualDias}",
                                style = MaterialTheme.typography.headlineMedium,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = if (usuarioActual.rachaActualDias == 1) "Día de racha" else "Días de racha",
                                style = MaterialTheme.typography.labelMedium,
                                color = Color(0xFFA5D6A7)
                            )
                        }
                    }

                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF388E3C)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                Icons.Default.Star,
                                contentDescription = null,
                                tint = Color(0xFFFFD54F),
                                modifier = Modifier.size(28.dp)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "$estrellasActuales",
                                style = MaterialTheme.typography.headlineMedium,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Estrellas",
                                style = MaterialTheme.typography.labelMedium,
                                color = Color(0xFFA5D6A7)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = { mainViewModel.registrarAccionDiaria() },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
                ) {
                    Text("Simular Acción Diaria", color = Color.White, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = { mostrarMisInsignias = true },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1B5E20))
                ) {
                    Text("Ver mis insignias", color = Color.White, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Top Investigadores",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color(0xFF1B5E20),
                                fontWeight = FontWeight.Bold
                            )
                            if (posicionActual > 0) {
                                Text(
                                    text = "Tu posición: #$posicionActual",
                                    style = MaterialTheme.typography.labelLarge,
                                    color = Color(0xFF4CAF50),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        if (leaderboard.isEmpty()) {
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "Aún no hay usuarios en la cima. ¡Sé el primero!",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray
                            )
                        } else {
                            Spacer(modifier = Modifier.height(16.dp))
                            for ((index, usuarioTop) in leaderboard.withIndex()) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "#${index + 1}",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = if (index == 0) Color(0xFFFFD700) else if (index == 1) Color.Gray else if (index == 2) Color(0xFFCD7F32) else Color(0xFF9E9E9E),
                                        modifier = Modifier.width(32.dp),
                                        fontWeight = FontWeight.Bold
                                    )
                                    Box(
                                        modifier = Modifier
                                            .size(40.dp)
                                            .clip(CircleShape)
                                            .background(Color(0xFFE8F5E9)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        SubcomposeAsyncImage(
                                            model = usuarioTop.avatarUrl,
                                            contentDescription = null,
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier.fillMaxSize(),
                                            loading = { Icon(Icons.Default.Person, contentDescription = null, tint = Color.Gray) },
                                            error = { Icon(Icons.Default.Person, contentDescription = null, tint = Color.Gray) }
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = usuarioTop.alias,
                                            style = MaterialTheme.typography.bodyMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF2E7D32)
                                        )
                                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                            for (insignia in usuarioTop.insignias.take(3)) {
                                                Box(
                                                    modifier = Modifier
                                                        .size(22.dp)
                                                        .clip(CircleShape)
                                                        .background(Color(0xFFFFF9DB)),
                                                    contentAlignment = Alignment.Center
                                                ) {
                                                    Icon(
                                                        imageVector = mapearIconoInsignia(insignia.iconoRef),
                                                        contentDescription = insignia.nombreVisible,
                                                        tint = Color(0xFFFFD54F),
                                                        modifier = Modifier.size(14.dp)
                                                    )
                                                }
                                            }
                                        }
                                    }
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = "${usuarioTop.estrellasPrestigio}",
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF388E3C)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Icon(
                                            Icons.Default.Star,
                                            contentDescription = null,
                                            tint = Color(0xFFFFD54F),
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                            }

                            if (posicionActual > 5 && filaUsuarioActual != null) {
                                HorizontalDivider(color = Color(0xFFBDBDBD), thickness = 1.dp)
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "#$posicionActual",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = Color(0xFF4CAF50),
                                        modifier = Modifier.width(32.dp),
                                        fontWeight = FontWeight.Bold
                                    )
                                    Box(
                                        modifier = Modifier
                                            .size(40.dp)
                                            .clip(CircleShape)
                                            .background(Color(0xFFE8F5E9)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        SubcomposeAsyncImage(
                                            model = filaUsuarioActual.avatarUrl,
                                            contentDescription = null,
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier.fillMaxSize(),
                                            loading = { Icon(Icons.Default.Person, contentDescription = null, tint = Color.Gray) },
                                            error = { Icon(Icons.Default.Person, contentDescription = null, tint = Color.Gray) }
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = filaUsuarioActual.alias,
                                            style = MaterialTheme.typography.bodyMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF2E7D32)
                                        )
                                        Text(
                                            text = "Tu posición",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = Color(0xFF757575)
                                        )
                                    }
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = "${filaUsuarioActual.estrellasPrestigio}",
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF388E3C)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Icon(
                                            Icons.Default.Star,
                                            contentDescription = null,
                                            tint = Color(0xFFFFD54F),
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        if (mostrarMisInsignias) {
            AlertDialog(
                onDismissRequest = { mostrarMisInsignias = false },
                confirmButton = {
                    TextButton(onClick = { mostrarMisInsignias = false }) {
                        Text(
                            text = "Cerrar",
                            color = Color(0xFF2E7D32),
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                containerColor = Color.White, // Fondo general del modal más limpio
                title = {
                    Text(
                        text = "Mis insignias",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color(0xFF1B5E20),
                        fontWeight = FontWeight.Bold
                    )
                },
                text = {
                    if (insigniasObtenidas.isEmpty()) {
                        Text("Aún no tienes insignias desbloqueadas.", color = Color.Gray)
                    } else {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .verticalScroll(rememberScrollState())
                        ) {
                            insigniasObtenidas.chunked(2).forEach { fila ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 12.dp)
                                        .height(IntrinsicSize.Max),
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    fila.forEach { insignia ->
                                        Card(
                                            modifier = Modifier
                                                .weight(1f)
                                                .fillMaxHeight(),
                                            shape = RoundedCornerShape(16.dp),
                                            colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F8E9)),
                                            border = BorderStroke(1.dp, Color(0xFFC8E6C9))
                                        ) {
                                            Column(
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .padding(12.dp),
                                                horizontalAlignment = Alignment.CenterHorizontally
                                            ) {
                                                Box(
                                                    modifier = Modifier
                                                        .size(48.dp)
                                                        .clip(CircleShape)
                                                        .background(Color(0xFFE8F5E9)),
                                                    contentAlignment = Alignment.Center
                                                ) {
                                                    Icon(
                                                        imageVector = mapearIconoInsignia(insignia.iconoRef),
                                                        contentDescription = insignia.nombreVisible,
                                                        tint = Color(0xFF2E7D32),
                                                        modifier = Modifier.size(28.dp)
                                                    )
                                                }

                                                Spacer(modifier = Modifier.height(8.dp))

                                                Text(
                                                    text = insignia.nombreVisible,
                                                    style = MaterialTheme.typography.titleSmall,
                                                    fontWeight = FontWeight.Bold,
                                                    color = Color(0xFF1B5E20),
                                                    textAlign = TextAlign.Center
                                                )

                                                Spacer(modifier = Modifier.height(6.dp))

                                                Text(
                                                    text = insignia.descripcion,
                                                    style = MaterialTheme.typography.bodySmall,
                                                    color = Color(0xFF424242),
                                                    textAlign = TextAlign.Center
                                                )

                                                Spacer(modifier = Modifier.weight(1f))
                                                Spacer(modifier = Modifier.height(10.dp))

                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.Center
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.Default.Info,
                                                        contentDescription = null,
                                                        tint = Color(0xFF81C784),
                                                        modifier = Modifier.size(14.dp)
                                                    )
                                                    Spacer(modifier = Modifier.width(4.dp))
                                                    Text(
                                                        text = "Obtenida:\n${insignia.fechaNotificacion}", // Salto de línea para que no se apriete
                                                        style = MaterialTheme.typography.labelSmall,
                                                        color = Color(0xFF616161),
                                                        textAlign = TextAlign.Center,
                                                        lineHeight = 14.sp
                                                    )
                                                }
                                            }
                                        }
                                    }

                                    // Relleno si la fila tiene un número impar de insignias
                                    if (fila.size == 1) {
                                        Spacer(modifier = Modifier.weight(1f))
                                    }
                                }
                            }
                        }
                    }
                }
            )
        }
    }

    AlertaInsigniaHost(
        insignias = insigniasNuevas,
        onTodasMostradas = { mainViewModel.limpiarInsignias() }
    )
}

private fun mapearIconoInsignia(ref: String): ImageVector {
    return when (ref) {
        "Icons.Default.Star" -> Icons.Default.Star
        "Icons.Default.School" -> Icons.Default.School
        "Icons.Default.EmojiEvents" -> Icons.Default.EmojiEvents
        "Icons.Default.LocalFireDepartment" -> Icons.Default.LocalFireDepartment
        else -> Icons.Default.LocalFireDepartment
    }
}
