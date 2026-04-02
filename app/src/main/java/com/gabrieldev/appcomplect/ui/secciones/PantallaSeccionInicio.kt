package com.gabrieldev.appcomplect.ui.secciones

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.gabrieldev.appcomplect.data.repository.UsuarioRepository
import com.gabrieldev.appcomplect.model.Usuario
import com.gabrieldev.appcomplect.model.UsuarioLeaderboard

@Composable
fun PantallaSeccionInicio(
    usuario: Usuario,
    usuarioRepository: UsuarioRepository
) {
    val scrollState = rememberScrollState()

    val tipoTexto = usuario.nombreRol.ifBlank { "Investigador" }

    val estrellasRequeridas = usuario.nivel?.estrellasRequeridas ?: 0
    val progresoObjetivo = if (estrellasRequeridas > 0)
        (usuario.estrellasPrestigio.toFloat() / estrellasRequeridas.toFloat()).coerceIn(0f, 1f)
    else 0f

    var progresoAnimado by remember { mutableFloatStateOf(0f) }
    val progresoState by animateFloatAsState(
        targetValue = progresoAnimado,
        animationSpec = tween(durationMillis = 1000),
        label = "progreso"
    )
    
    var leaderboard by remember { mutableStateOf<List<UsuarioLeaderboard>>(emptyList()) }
    var posicionActual by remember { mutableIntStateOf(0) }
    
    LaunchedEffect(progresoObjetivo) {
        progresoAnimado = progresoObjetivo
    }

    LaunchedEffect(Unit) {
        leaderboard = usuarioRepository.obtenerLeaderboardTop5()
        posicionActual = usuarioRepository.obtenerRankingPosicion(usuario.estrellasPrestigio)
    }

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
                .padding(horizontal = 24.dp, vertical = 28.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(96.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF4CAF50)),
                    contentAlignment = Alignment.Center
                ) {
                    SubcomposeAsyncImage(
                        model = usuario.avatarUrl.ifBlank { usuario.idAvatar },
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
                    text = if (usuario.alias == "..." || usuario.alias.isBlank()) "Cargando..." else "¡Hola, ${usuario.alias}!",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )

                if (usuario.nombre.isNotBlank()) {
                    Text(
                        text = "${usuario.nombre} ${usuario.apellidoPaterno}".trim(),
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
                            text = usuario.nivel?.nombreRango?.takeIf { it.isNotBlank() } ?: "Sin nivel asignado",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color(0xFF2E7D32),
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = "${usuario.estrellasPrestigio} / ${if (estrellasRequeridas > 0) estrellasRequeridas else "—"}",
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

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF2E7D32)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "🔥", fontSize = 28.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "${usuario.rachaActualDias}",
                            style = MaterialTheme.typography.headlineMedium,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = if (usuario.rachaActualDias == 1) "día de racha" else "días de racha",
                            style = MaterialTheme.typography.labelMedium,
                            color = Color(0xFFA5D6A7)
                        )
                    }
                }

                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF388E3C)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            Icons.Default.Star,
                            contentDescription = null,
                            tint = Color(0xFFFFD54F),
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "${usuario.estrellasPrestigio}",
                            style = MaterialTheme.typography.headlineMedium,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "estrellas",
                            style = MaterialTheme.typography.labelMedium,
                            color = Color(0xFFA5D6A7)
                        )
                    }
                }
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
                                        loading = { Icon(
                                            Icons.Default.Person,contentDescription = null, tint = Color.Gray) },
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
                    }
                }
            }
        }
    }
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
