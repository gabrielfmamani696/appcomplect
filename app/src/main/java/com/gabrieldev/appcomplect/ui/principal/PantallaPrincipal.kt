package com.gabrieldev.appcomplect.ui.principal

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
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
    usuarioRepository: UsuarioRepository
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val rutaActual = navBackStackEntry?.destination?.route
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(260.dp),
                drawerContainerColor = Color(0xFF1B5E20)
            ) {
                Spacer(modifier = Modifier.height(56.dp))
                Text(
                    text = "Menú",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
                )
                HorizontalDivider(
                    color = Color(0xFF4CAF50),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))

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
                        usuarioRepository = usuarioRepository
                    )
                }
            }

            // Pestaña lateral izquierda para abrir el drawer
            Box(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .background(
                        color = Color(0xFF4CAF50),
                        shape = RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp)
                    )
                    .clickable { scope.launch { drawerState.open() } }
                    .padding(horizontal = 6.dp, vertical = 18.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Abrir menú",
                    tint = Color.White,
                    modifier = Modifier.size(22.dp)
                )
            }
        }
    }
}
