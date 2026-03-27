package com.gabrieldev.appcomplect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gabrieldev.appcomplect.data.repository.AvatarRepository
import com.gabrieldev.appcomplect.data.repository.UsuarioRepository
import com.gabrieldev.appcomplect.ui.RegistroUsuario
import com.gabrieldev.appcomplect.ui.theme.AppcomplectTheme
import com.google.firebase.dataconnect.generated.DefaultConnector
import com.google.firebase.dataconnect.generated.instance
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    // Inicializamos Data Connect
    private val connector by lazy { 
        val conn = DefaultConnector.instance 
        // Descomenta esta linea si tu Seed Data fue ejecutada en el Emulador Local (Localhost):
        // conn.dataConnect.useEmulator("10.0.2.2", 9399)
        conn
    }
    
    // Inyectamos el conector a los Repositorios (MVVM Básico Manual)
    private val repositorioUsuario by lazy { UsuarioRepository(connector, applicationContext) }
    private val repositorioAvatar by lazy { AvatarRepository(connector) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            //oninit
            LaunchedEffect(Unit) {
                repositorioUsuario.verificarSesion()
            }

            AppcomplectTheme {
                val usuarioActivo by repositorioUsuario.usuarioActivo.collectAsState()
                val estaCargando by repositorioUsuario.estaCargando.collectAsState()
                
                val scope = rememberCoroutineScope()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
                        if (estaCargando) {
                            // Pantalla de carga
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        } else {
                            if (usuarioActivo != null) {
                                // PANTALLA PRINCIPAL BÁSICA (El usuario ya tiene sesión)
                                Column(
                                    modifier = Modifier.align(Alignment.Center),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "¡Es bueno tenerte de vuelta!",
                                        style = MaterialTheme.typography.titleLarge
                                    )
                                    Text(
                                        text = "Periodista: ${usuarioActivo!!.alias}",
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                    
                                    Spacer(modifier = Modifier.height(24.dp))
                                    
                                    Button(onClick = {
                                        scope.launch { repositorioUsuario.cerrarSesion() }
                                    }) {
                                        Text("Cerrar Sesión")
                                    }
                                }
                            } else {
                                // PANTALLA DE REGISTRO (No hay usuario guardado localmente)
                                RegistroUsuario(
                                    usuarioRepository = repositorioUsuario,
                                    avatarRepository = repositorioAvatar,
                                    alTerminar = { }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
