package com.gabrieldev.appcomplect.ui.secciones.archivos

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
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.gabrieldev.appcomplect.model.Usuario
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.IconButton
import com.gabrieldev.appcomplect.ui.theme.ColorFondo
import com.gabrieldev.appcomplect.ui.theme.ColorVerde
import com.gabrieldev.appcomplect.ui.theme.ColorVerdeClaro
import com.gabrieldev.appcomplect.ui.theme.ColorVerdeMedio

@Composable
fun PantallaCuestionario(
    viewModel: ArchivoDetalleViewModel,
    usuarioActivo: Usuario?,
    onSalir: () -> Unit
) {
    val estado by viewModel.estado.collectAsState()
    val indiceActual by viewModel.indicePreguntaActual.collectAsState()
    val respuestas by viewModel.respuestasUsuario.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorFondo)
    ) {
        when (val st = estado) {
            is EstadoCuestionario.Cargando -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = ColorVerdeMedio)
            }
            is EstadoCuestionario.Error -> {
                Text(
                    "Error: ${st.mensaje}",
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.error
                )
            }
            is EstadoCuestionario.Finalizado -> {
                PantallaResultado(
                    nota = st.nota,
                    aprobado = st.aprobado,
                    estrellasGanadas = st.estrellasGanadas,
                    onVolver = onSalir
                )
            }
            is EstadoCuestionario.CuestionarioActivo -> {
                val cuestionario = st.datos.cuestionario
                if (cuestionario == null) {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Este archivo no tiene un cuestionario.")
                            Spacer(Modifier.height(16.dp))
                            Button(onClick = onSalir, colors = ButtonDefaults.buttonColors(containerColor = ColorVerdeMedio)) {
                                Text("Cerrar")
                            }
                        }
                    }
                    return@Box
                }
                val preguntaActual = cuestionario.preguntas.getOrNull(indiceActual) ?: return@Box
                val totalPreguntas = cuestionario.preguntas.size

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(ColorVerde)
                            .padding(20.dp)
                    ) {
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = cuestionario.tituloQuiz,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = Color.White.copy(alpha = 0.8f)
                                )
                                IconButton(onClick = onSalir) {
                                    Icon(Icons.Default.Close, contentDescription = "Cerrar", tint = Color.White)
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            LinearProgressIndicator(
                                progress = { (indiceActual + 1).toFloat() / totalPreguntas },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(4.dp)),
                                color = ColorVerdeMedio,
                                trackColor = Color.White.copy(alpha = 0.3f)
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Pregunta ${indiceActual + 1} de $totalPreguntas",
                                style = MaterialTheme.typography.labelMedium,
                                color = Color.White
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = preguntaActual.enunciado,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = ColorVerde,
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        preguntaActual.respuestas.forEach { respuesta ->
                            val seleccionada = respuestas[preguntaActual.id] == respuesta.id
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .selectable(
                                        selected = seleccionada,
                                        onClick = {
                                            viewModel.seleccionarRespuesta(preguntaActual.id, respuesta.id)
                                        }
                                    ),
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = if (seleccionada) ColorVerdeClaro else Color.White
                                ),
                                elevation = CardDefaults.cardElevation(defaultElevation = if (seleccionada) 0.dp else 2.dp),
                                border = if (seleccionada) androidx.compose.foundation.BorderStroke(2.dp, ColorVerdeMedio) else null
                            ) {
                                Row(
                                    modifier = Modifier.padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    RadioButton(
                                        selected = seleccionada,
                                        onClick = null,
                                        colors = RadioButtonDefaults.colors(
                                            selectedColor = ColorVerdeMedio,
                                            unselectedColor = Color(0xFFBDBDBD)
                                        )
                                    )
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Text(
                                        text = respuesta.textoOpcion,
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = if (seleccionada) ColorVerde else Color.DarkGray
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        if (indiceActual > 0) {
                            OutlinedButton(
                                onClick = { viewModel.anteriorPregunta() },
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text("Anterior", color = ColorVerde)
                            }
                        } else {
                            Spacer(modifier = Modifier.width(1.dp))
                        }

                        if (indiceActual < totalPreguntas - 1) {
                            val respondida = respuestas.containsKey(preguntaActual.id)
                            Button(
                                onClick = { viewModel.siguientePregunta() },
                                enabled = respondida,
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = ColorVerdeMedio,
                                    disabledContainerColor = Color.Gray.copy(alpha = 0.5f)
                                )
                            ) {
                                Text("Siguiente", fontWeight = FontWeight.Bold)
                            }
                        } else {
                            Button(
                                onClick = {
                                    val idUsuario = usuarioActivo?.uuidSesion ?: ""
                                    viewModel.finalizarCuestionario(idUsuario)
                                },
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = ColorVerde),
                                enabled = respuestas.size == totalPreguntas
                            ) {
                                Text("Finalizar", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
            else -> {}
        }
    }
}

@Composable
private fun PantallaResultado(
    nota: Int,
    aprobado: Boolean,
    estrellasGanadas: Int,
    onVolver: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorFondo),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(32.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(Color.White)
                .padding(32.dp)
        ) {
            Text(
                text = if (aprobado) "🎉" else "📚",
                style = MaterialTheme.typography.displayMedium,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = if (aprobado) "¡Aprobado!" else "Sigue intentando",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = if (aprobado) ColorVerdeMedio else MaterialTheme.colorScheme.error
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "$nota / 100",
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
                color = ColorVerde
            )

            if (aprobado && estrellasGanadas > 0) {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = null,
                        tint = Color(0xFFFFC107),
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "+$estrellasGanadas estrellas",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color(0xFFF57F17),
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            Button(
                onClick = onVolver,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = ColorVerdeMedio)
            ) {
                Text("Volver a los Archivos", fontWeight = FontWeight.Bold)
            }
        }
    }
}
