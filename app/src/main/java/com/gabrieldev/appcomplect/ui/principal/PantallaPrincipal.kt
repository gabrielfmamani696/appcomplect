package com.gabrieldev.appcomplect.ui.principal

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.SubcomposeAsyncImage
import com.gabrieldev.appcomplect.data.repository.ArchivoRepository
import com.gabrieldev.appcomplect.data.repository.AvatarRepository
import com.gabrieldev.appcomplect.data.repository.InsigniaRepository
import com.gabrieldev.appcomplect.data.repository.UsuarioRepository
import com.gabrieldev.appcomplect.model.Usuario
import com.gabrieldev.appcomplect.ui.AppViewModelFactory
import com.gabrieldev.appcomplect.ui.MainViewModel
import com.gabrieldev.appcomplect.ui.navegacion.Rutas
import com.gabrieldev.appcomplect.ui.secciones.PantallaSeccionInicio
import com.gabrieldev.appcomplect.ui.secciones.archivos.ArchivoDetalleViewModel
import com.gabrieldev.appcomplect.ui.secciones.archivos.ArchivoDetalleViewModelFactory
import com.gabrieldev.appcomplect.ui.secciones.archivos.ArchivosViewModel
import com.gabrieldev.appcomplect.ui.secciones.archivos.CrearArchivoViewModel
import com.gabrieldev.appcomplect.ui.secciones.archivos.CrearArchivoViewModelFactory
import com.gabrieldev.appcomplect.ui.secciones.archivos.PantallaArchivos
import com.gabrieldev.appcomplect.ui.secciones.archivos.PantallaCarrusel
import com.gabrieldev.appcomplect.ui.secciones.archivos.PantallaCrearEditarArchivo
import com.gabrieldev.appcomplect.ui.secciones.perfilpersonal.PantallaPerfil
import com.gabrieldev.appcomplect.ui.secciones.perfilpersonal.PerfilViewModel
import com.gabrieldev.appcomplect.ui.theme.ColorVerde
import kotlinx.coroutines.launch

@Composable
fun PantallaPrincipal(
    usuario: Usuario,
    usuarioRepository: UsuarioRepository,
    avatarRepository: AvatarRepository,
    archivoRepository: ArchivoRepository,
    insigniaRepository: InsigniaRepository,
    mainViewModel: MainViewModel
) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var mostrarPerfiles by remember { mutableStateOf(false) }

    val sesionesGuardadas by usuarioRepository.sesionesGuardadas.collectAsState()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val items = listOf(
        Rutas.Inicio,
        Rutas.Archivos,
        Rutas.Perfil
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = ColorVerde,
                drawerContentColor = Color.White
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        if (usuario.avatarUrl.isNotEmpty()) {
                            SubcomposeAsyncImage(
                                model = usuario.avatarUrl,
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize()
                            )
                        } else {
                            Text("👤", style = MaterialTheme.typography.headlineLarge, color = Color.Gray)
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = usuario.alias,
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White
                    )
                    Text(
                        text = usuario.nombreRol,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.8f)
                    )

                    if (sesionesGuardadas.size > 1) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { mostrarPerfiles = !mostrarPerfiles }
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = if (mostrarPerfiles) "Ocultar cuentas" else "Cambiar cuenta",
                                style = MaterialTheme.typography.labelMedium,
                                color = Color.White
                            )
                            Icon(
                                imageVector = if (mostrarPerfiles) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        if (mostrarPerfiles) {
                            Spacer(modifier = Modifier.height(12.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
                            ) {
                                sesionesGuardadas.filter { it.id != usuario.uuidSesion }.forEach { sesion ->
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier.clickable {
                                            scope.launch {
                                                usuarioRepository.cambiarSesion(sesion.id)
                                                drawerState.close()
                                                mostrarPerfiles = false
                                            }
                                        }
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(44.dp)
                                                .clip(CircleShape)
                                                .background(Color.White.copy(alpha = 0.2f)),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            if (sesion.avatarUrl.isNotEmpty()) {
                                                SubcomposeAsyncImage(
                                                    model = sesion.avatarUrl,
                                                    contentDescription = null,
                                                    modifier = Modifier.fillMaxSize()
                                                )
                                            } else {
                                                Text("👤", style = MaterialTheme.typography.bodyLarge, color = Color.White)
                                            }
                                        }
                                        Text(
                                            text = sesion.alias,
                                            style = MaterialTheme.typography.labelSmall,
                                            color = Color.White.copy(alpha = 0.9f)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                items.forEach { screen ->
                    val selected = currentRoute == screen.ruta
                    NavigationDrawerItem(
                        icon = {
                            Icon(
                                when (screen) {
                                    Rutas.Inicio -> Icons.Default.Home
                                    Rutas.Archivos -> Icons.AutoMirrored.Filled.List
                                    Rutas.Perfil -> Icons.Default.Person
                                    else -> Icons.Default.Home
                                },
                                contentDescription = null,
                                tint = if (selected) ColorVerde else Color.White
                            )
                        },
                        label = {
                            Text(
                                text = screen.javaClass.simpleName.removeSuffix("$"),
                                color = if (selected) ColorVerde else Color.White
                            )
                        },
                        selected = selected,
                        onClick = {
                            navController.navigate(screen.ruta) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                            scope.launch { drawerState.close() }
                        },
                        colors = NavigationDrawerItemDefaults.colors(
                            selectedContainerColor = Color.White,
                            unselectedContainerColor = Color.Transparent,
                            selectedTextColor = ColorVerde,
                            unselectedTextColor = Color.White
                        ),
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }

                Spacer(modifier = Modifier.weight(1f))
                HorizontalDivider(color = Color.White.copy(alpha = 0.3f))
                NavigationDrawerItem(
                    label = { Text("Cerrar Sesión", color = Color.White) },
                    selected = false,
                    onClick = {
                        scope.launch {
                            usuarioRepository.cerrarSesion()
                        }
                    },
                    colors = NavigationDrawerItemDefaults.colors(
                        unselectedContainerColor = Color.Transparent,
                        unselectedTextColor = Color.White
                    ),
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
                Spacer(modifier = Modifier.height(16.dp))
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
                        usuarioRepository = usuarioRepository,
                        mainViewModel = mainViewModel
                    )
                }
                composable(Rutas.Archivos.ruta) {
                    val factory = remember { AppViewModelFactory(usuarioRepository, avatarRepository, archivoRepository, insigniaRepository) }
                    val archivosViewModel: ArchivosViewModel = viewModel(factory = factory)
                    PantallaArchivos(
                        navController = navController,
                        viewModel = archivosViewModel
                    )
                }
                composable(Rutas.Perfil.ruta) {
                    val factory = remember { AppViewModelFactory(usuarioRepository, avatarRepository, archivoRepository, insigniaRepository) }
                    val perfilViewModel: PerfilViewModel = viewModel(factory = factory)
                    PantallaPerfil(
                        usuario = usuario,
                        viewModel = perfilViewModel
                    )
                }
                composable(Rutas.ArchivoDetalle.ruta) { backStackEntry ->
                    val id = backStackEntry.arguments?.getString("idArchivo") ?: ""
                    val factory = remember { ArchivoDetalleViewModelFactory(archivoRepository, usuarioRepository, insigniaRepository, id) }
                    val detailViewModel: ArchivoDetalleViewModel = viewModel(factory = factory)
                    PantallaCarrusel(
                        viewModel = detailViewModel,
                        onNavigateBack = { navController.popBackStack() }
                    )
                }
                composable(Rutas.ArchivoCrearDivulgacion.ruta) {
                    val factory = remember { CrearArchivoViewModelFactory(archivoRepository, usuarioRepository, null) }
                    val crearViewModel: CrearArchivoViewModel = viewModel(factory = factory)
                    PantallaCrearEditarArchivo(
                        viewModel = crearViewModel,
                        onNavigateBack = { navController.popBackStack() }
                    )
                }
                composable(Rutas.ArchivoEditarDivulgacion.ruta) { backStackEntry ->
                    val id = backStackEntry.arguments?.getString("idArchivo") ?: ""
                    val factory = remember { CrearArchivoViewModelFactory(archivoRepository, usuarioRepository, id) }
                    val editarViewModel: CrearArchivoViewModel = viewModel(factory = factory)
                    PantallaCrearEditarArchivo(
                        viewModel = editarViewModel,
                        onNavigateBack = { navController.popBackStack() }
                    )
                }
            }

            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.TopStart)
            ) {
                IconButtonCustom(
                    onClick = { scope.launch { drawerState.open() } },
                    icon = Icons.Default.Menu
                )
            }
        }
    }
}

@Composable
fun IconButtonCustom(onClick: () -> Unit, icon: ImageVector) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFE8F5E9))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(icon, contentDescription = null, tint = ColorVerde)
    }
}
