package com.gabrieldev.appcomplect.ui.secciones.archivos

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.gabrieldev.appcomplect.ui.navegacion.Rutas

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaArchivos(
    navController: NavController
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Archivos", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF2E7D32))
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {  },
                containerColor = Color(0xFF4CAF50)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Nuevo archivo", tint = Color.White)
            }
        },
        containerColor = Color(0xFFF1F8F1)
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {
        }
    }
}
