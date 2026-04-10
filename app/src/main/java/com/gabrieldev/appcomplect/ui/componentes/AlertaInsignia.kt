package com.gabrieldev.appcomplect.ui.componentes

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.gabrieldev.appcomplect.model.InsigniaObtenida
import kotlinx.coroutines.delay

private const val DURACION_VISIBLE_MS = 4000L

@Composable
fun AlertaInsigniaHost(
    insignias: List<InsigniaObtenida>,
    onTodasMostradas: () -> Unit
) {
    var indiceActual by remember { mutableStateOf(0) }
    var visible by remember { mutableStateOf(false) }

    val insigniaActual = insignias.getOrNull(indiceActual)

    LaunchedEffect(insignias) {
        if (insignias.isNotEmpty()) {
            indiceActual = 0
            visible = true
        }
    }

    LaunchedEffect(indiceActual, insignias.size) {
        if (insignias.isNotEmpty() && indiceActual < insignias.size) {
            visible = true
            delay(DURACION_VISIBLE_MS)
            visible = false
            delay(400)
            if (indiceActual < insignias.size - 1) {
                indiceActual++
            } else {
                onTodasMostradas()
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        AnimatedVisibility(
            visible = visible && insigniaActual != null,
            enter = slideInVertically(initialOffsetY = { it }, animationSpec = tween(450)) + fadeIn(tween(300)),
            exit = slideOutVertically(targetOffsetY = { it }, animationSpec = tween(350)) + fadeOut(tween(250))
        ) {
            if (insigniaActual != null) {
                TarjetaInsignia(insignia = insigniaActual)
            }
        }
    }
}

@Composable
private fun TarjetaInsignia(insignia: InsigniaObtenida) {
    val icono = resolverIcono(insignia.iconoRef)

    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 32.dp)
            .shadow(elevation = 16.dp, shape = RoundedCornerShape(20.dp))
            .clip(RoundedCornerShape(20.dp))
            .background(
                Brush.horizontalGradient(
                    colors = listOf(Color(0xFF1B5E20), Color(0xFF2E7D32))
                )
            )
            .padding(20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color(0x33FFFFFF)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icono,
                    contentDescription = null,
                    tint = Color(0xFFFFD54F),
                    modifier = Modifier.size(32.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "¡Insignia desbloqueada!",
                    style = MaterialTheme.typography.labelMedium,
                    color = Color(0xFF69F0AE),
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = insignia.nombreVisible,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = insignia.descripcion,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.75f)
                )
            }
        }
    }
}

private fun resolverIcono(iconoRef: String): ImageVector = when {
    iconoRef.contains("EmojiEvents") -> Icons.Default.EmojiEvents
    iconoRef.contains("School")      -> Icons.Default.School
    iconoRef.contains("Star")        -> Icons.Default.Star
    iconoRef.contains("Fire") || iconoRef.contains("LocalFire") -> Icons.Default.LocalFireDepartment
    else -> Icons.Default.EmojiEvents
}
