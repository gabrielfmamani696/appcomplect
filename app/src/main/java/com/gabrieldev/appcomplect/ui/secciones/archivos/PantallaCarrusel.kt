package com.gabrieldev.appcomplect.ui.secciones.archivos

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.gabrieldev.appcomplect.model.Tarjeta
import com.gabrieldev.appcomplect.ui.componentes.AlertaInsigniaHost
import com.gabrieldev.appcomplect.ui.theme.ColorFondo
import com.gabrieldev.appcomplect.ui.theme.ColorVerde
import com.gabrieldev.appcomplect.ui.theme.ColorVerdeClaro
import com.gabrieldev.appcomplect.ui.theme.ColorVerdeMedio
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun PantallaCarrusel(
    viewModel: ArchivoDetalleViewModel,
    onNavigateBack: () -> Unit
) {
    val estado by viewModel.estado.collectAsState()
    val contenido by viewModel.contenido.collectAsState()
    val mostrando by viewModel.mostrando.collectAsState()
    val usuarioActivo by viewModel.usuarioActivo.collectAsState()
    val insigniasNuevas by viewModel.insigniasNuevas.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            mostrando -> {
                PantallaCuestionario(
                    viewModel = viewModel,
                    usuarioActivo = usuarioActivo,
                    onSalir = { viewModel.reiniciarCuestionario(onVolverAArchivos = onNavigateBack) }
                )
            }
            estado is EstadoCuestionario.Cargando -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(ColorFondo),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = ColorVerdeMedio)
                }
            }
            estado is EstadoCuestionario.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(ColorFondo)
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Error al cargar contenido",
                            style = MaterialTheme.typography.titleLarge,
                            color = ColorVerde,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = (estado as EstadoCuestionario.Error).mensaje,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Button(
                            onClick = onNavigateBack,
                            colors = ButtonDefaults.buttonColors(containerColor = ColorVerdeMedio),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Cerrar y Volver", color = Color.White)
                        }
                    }
                }
            }
            else -> {
                val tarjetas = contenido?.tarjetas ?: emptyList()
                val tieneCuestionario = contenido?.cuestionario != null && contenido!!.cuestionario!!.preguntas.isNotEmpty()
                val titulo = contenido?.titulo ?: ""
                val tema = contenido?.tema
                val descripcion = contenido?.descripcion
                val imagenUrl = contenido?.imagenUrl
                val fechaCreacion = contenido?.fechaCreacion

                val mostrarPortada = !tema.isNullOrBlank() || !descripcion.isNullOrBlank()
                val totalPaginas = if (mostrarPortada) tarjetas.size + 1 else tarjetas.size.coerceAtLeast(1)

                val pagerState = rememberPagerState(pageCount = { totalPaginas })

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(ColorFondo)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(ColorVerde)
                            .padding(horizontal = 8.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = onNavigateBack) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver", tint = Color.White)
                        }
                        Text(
                            text = titulo,
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(onClick = { viewModel.reiniciarCuestionario(onVolverAArchivos = onNavigateBack) }) {
                            Icon(Icons.Default.Close, contentDescription = "Cerrar", tint = Color.White)
                        }
                    }

                    if (tarjetas.isEmpty() && !mostrarPortada) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text("Este archivo no tiene tarjetas aún.", color = Color(0xFF9E9E9E))
                        }
                    } else {
                        HorizontalPager(
                            state = pagerState,
                            modifier = Modifier
                                .weight(1f)
                                .padding(16.dp)
                        ) { page ->
                            if (mostrarPortada && page == 0) {
                                ComponentePortadaVista(
                                    titulo = titulo,
                                    tema = tema,
                                    descripcion = descripcion,
                                    imagenUrl = imagenUrl,
                                    fechaCreacion = fechaCreacion
                                )
                            } else {
                                val tarjetaIdx = if (mostrarPortada) page - 1 else page
                                ComponenteTarjetaVista(tarjeta = tarjetas[tarjetaIdx])
                            }
                        }

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 32.dp, start = 16.dp, end = 16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                repeat(totalPaginas) { index ->
                                    val color by animateColorAsState(
                                        targetValue = if (index == pagerState.currentPage) ColorVerdeMedio else Color(0xFFB0BEC5),
                                        animationSpec = tween(300),
                                        label = "dot_color"
                                    )
                                    Box(
                                        modifier = Modifier
                                            .size(if (index == pagerState.currentPage) 10.dp else 7.dp)
                                            .clip(CircleShape)
                                            .background(color)
                                    )
                                }
                            }

                            Text(
                                text = "Tarjeta ${pagerState.currentPage + 1} de $totalPaginas",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF757575)
                            )

                            if (pagerState.currentPage == totalPaginas - 1 && tieneCuestionario) {
                                Button(
                                    onClick = { viewModel.iniciarCuestionario(usuarioActivo?.uuidSesion) },
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = ColorVerdeMedio)
                                ) {
                                    Text("Comenzar Evaluación", fontWeight = FontWeight.Bold)
                                }
                            } else if (pagerState.currentPage == totalPaginas - 1 && !tieneCuestionario) {
                                Text(
                                    text = "Este archivo no tiene cuestionario.",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFF9E9E9E)
                                )
                            }
                        }
                    }
                }
            }
        }

        AlertaInsigniaHost(
            insignias = insigniasNuevas,
            onTodasMostradas = { viewModel.limpiarInsignias() }
        )
    }
}

@Composable
private fun ComponentePortadaVista(
    titulo: String,
    tema: String?,
    descripcion: String?,
    imagenUrl: String?,
    fechaCreacion: Long?
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(24.dp))
            .background(ColorVerdeClaro),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            if (!imagenUrl.isNullOrBlank()) {
                SubcomposeAsyncImage(
                    model = imagenUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.5f),
                    loading = {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(ColorVerdeClaro),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = ColorVerdeMedio)
                        }
                    }
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = titulo,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = ColorVerde,
                    textAlign = TextAlign.Center
                )

                if (!tema.isNullOrBlank()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = tema,
                        style = MaterialTheme.typography.titleMedium,
                        color = ColorVerdeMedio,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center
                    )
                }

                if (!descripcion.isNullOrBlank()) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = descripcion,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.DarkGray,
                        textAlign = TextAlign.Center,
                        lineHeight = 24.sp
                    )
                }

                if (fechaCreacion != null) {
                    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    val fechaStr = sdf.format(Date(fechaCreacion))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Publicado el $fechaStr",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Desliza para empezar →",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF757575)
                )
            }
        }
    }
}

@Composable
private fun ComponenteTarjetaVista(tarjeta: Tarjeta) {
    val tipoNorm = tarjeta.tipoFondo.lowercase().trim()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(24.dp))
    ) {
        if (tipoNorm == "imagen") {
            Column(modifier = Modifier.fillMaxSize()) {

                SubcomposeAsyncImage(
                    model = gsUrlToHttps(tarjeta.dataFondo),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.5f),
                    loading = {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(ColorVerdeClaro),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                color = ColorVerdeMedio,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    },
                    error = {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(ColorVerdeClaro)
                        )
                    }
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(ColorVerdeClaro),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)
                    ) {
                        Text(
                            text = tarjeta.contenidoTexto,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.SemiBold,
                            color = ColorVerde,
                            textAlign = TextAlign.Center,
                            lineHeight = 26.sp
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Desliza para continuar →",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF757575)
                        )
                    }
                }
            }
        } else {

            val fondoColor = try {
                Color(android.graphics.Color.parseColor(tarjeta.dataFondo))
            } catch (e: Exception) {
                ColorVerdeClaro
            }

            val esFondoClaro = fondoColor.luminance() > 0.5f

            val colorTextoPrincipal = if (esFondoClaro) ColorVerde else Color.White
            val colorTextoSecundario = if (esFondoClaro) Color(0xFF757575) else Color.White.copy(alpha = 0.7f)

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Brush.verticalGradient(listOf(fondoColor, fondoColor.copy(alpha = 0.8f)))),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(32.dp)
                ) {
                    Text(
                        text = tarjeta.contenidoTexto,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = colorTextoPrincipal,
                        textAlign = TextAlign.Center,
                        lineHeight = 32.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Desliza para continuar →",
                        style = MaterialTheme.typography.bodySmall,
                        color = colorTextoSecundario
                    )
                }
            }
        }
    }
}

private fun gsUrlToHttps(url: String): String {
    if (!url.startsWith("gs://")) return url
    val withoutPrefix = url.removePrefix("gs://")
    val slashIdx = withoutPrefix.indexOf('/')
    if (slashIdx < 0) return url
    val bucket = withoutPrefix.substring(0, slashIdx)
    val path = withoutPrefix.substring(slashIdx + 1)
    val encodedPath = path.replace("/", "%2F").replace(" ", "%20")
    return "https://firebasestorage.googleapis.com/v0/b/$bucket/o/$encodedPath?alt=media"
}