package com.gabrieldev.appcomplect.ui.principal

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.SubcomposeAsyncImage
import com.gabrieldev.appcomplect.data.repository.AvatarRepository
import com.gabrieldev.appcomplect.data.repository.UsuarioRepository
import com.gabrieldev.appcomplect.model.Usuario
import com.gabrieldev.appcomplect.ui.navegacion.Rutas
import com.gabrieldev.appcomplect.ui.secciones.PantallaSeccionInicio
import com.gabrieldev.appcomplect.ui.secciones.archivos.PantallaArchivos
import com.gabrieldev.appcomplect.ui.secciones.perfilpersonal.PantallaPerfil
import kotlinx.coroutines.launch

@Composable
fun PantallaPrincipal(
    usuario: Usuario,
    usuarioRepository: UsuarioRepository,
    avatarRepository: AvatarRepository
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val rutaActual = navBackStackEntry?.destination?.route
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    
    val sesionesLocales by usuarioRepository.sesionesGuardadas.collectAsState()
    var mostrarSesiones by remember { mutableStateOf(false) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(260.dp),
                drawerContainerColor = Color(0xFF1B5E20)
            ) {
                Spacer(modifier = Modifier.height(32.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .clickable { mostrarSesiones = !mostrarSesiones },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SubcomposeAsyncImage(
                        model = usuario.avatarUrl,
                        contentDescription = null,
                        modifier = Modifier.size(40.dp).clip(CircleShape),
                        error = { Icon(Icons.Default.Person, contentDescription = null, tint = Color.White) }
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = usuario.alias,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White,
                        modifier = Modifier.weight(1f)
                    )
                    Icon(
                        imageVector = if (mostrarSesiones) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                        tint = Color.White
                    )
                }

                if (mostrarSesiones) {
                    Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp)) {
                        sesionesLocales.filter { it.id != usuario.uuidSesion }.forEach { sesGuardada ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                                    .clickable {
                                        scope.launch { 
                                            drawerState.close()
                                            usuarioRepository.cambiarSesion(sesGuardada.id)
                                        }
                                    },
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                SubcomposeAsyncImage(
                                    model = sesGuardada.avatarUrl,
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp).clip(CircleShape),
                                    error = { Icon(Icons.Default.Person, contentDescription = null, tint = Color.LightGray) }
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(text = sesGuardada.alias, color = Color.LightGray, style = MaterialTheme.typography.bodyMedium)
                            }
                        }
                    }
                }

                HorizontalDivider(
                    color = Color(0xFF4CAF50),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )

                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = null, tint = Color.White) },
                    label = { Text("Inicio", color = Color.White) },
                    selected = rutaActual == Rutas.Inicio.ruta,
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate(Rutas.Inicio.ruta) {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    modifier = Modifier.padding(horizontal = 12.dp),
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = Color(0xFF4CAF50),
                        unselectedContainerColor = Color.Transparent
                    )
                )

                NavigationDrawerItem(
                    icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = null, tint = Color.White) },
                    label = { Text("Archivos", color = Color.White) },
                    selected = rutaActual == Rutas.Archivos.ruta,
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate(Rutas.Archivos.ruta) {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    modifier = Modifier.padding(horizontal = 12.dp),
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = Color(0xFF4CAF50),
                        unselectedContainerColor = Color.Transparent
                    )
                )

                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = null, tint = Color.White) },
                    label = { Text("Perfil", color = Color.White) },
                    selected = rutaActual == Rutas.Perfil.ruta,
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate(Rutas.Perfil.ruta) {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    modifier = Modifier.padding(horizontal = 12.dp),
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = Color(0xFF4CAF50),
                        unselectedContainerColor = Color.Transparent
                    )
                )
            }
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            NavHost(
                navController = navController,
                startDestination = Rutas.Inicio.ruta,
                modifier = Modifier.fillMaxSize()
            ) {
                composable(Rutas.Inicio.ruta) {
                    PantallaSeccionInicio(
                        usuario = usuario,
                        usuarioRepository = usuarioRepository
                    )
                }
                composable(Rutas.Archivos.ruta) {
                    PantallaArchivos(navController = navController)
                }
                composable(Rutas.Perfil.ruta) {
                    PantallaPerfil(
                        usuario = usuario,
                        usuarioRepository = usuarioRepository,
                        avatarRepository = avatarRepository
                    )
                }
            }

            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .background(
                        color = Color(0xFF4CAF50),
                        shape = RoundedCornerShape(bottomEnd = 16.dp)
                    )
                    .clickable { scope.launch { drawerState.open() } }
                    .padding(horizontal = 14.dp, vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Abrir menú",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}
