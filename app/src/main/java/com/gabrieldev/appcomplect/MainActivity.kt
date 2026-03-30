package com.gabrieldev.appcomplect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.gabrieldev.appcomplect.data.repository.AvatarRepository
import com.gabrieldev.appcomplect.data.repository.UsuarioRepository
import com.gabrieldev.appcomplect.ui.RegistroUsuario
import com.gabrieldev.appcomplect.ui.principal.PantallaPrincipal
import com.gabrieldev.appcomplect.ui.theme.AppcomplectTheme
import com.google.firebase.dataconnect.generated.DefaultConnector
import com.google.firebase.dataconnect.generated.instance

class MainActivity : ComponentActivity() {

    private val connector by lazy {
        val conn = DefaultConnector.instance
        // conn.dataConnect.useEmulator("10.0.2.2", 9399)
        conn
    }

    private val repositorioUsuario by lazy { UsuarioRepository(connector, applicationContext) }
    private val repositorioAvatar by lazy { AvatarRepository(connector) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            LaunchedEffect(Unit) {
                repositorioUsuario.verificarSesion()
            }

            AppcomplectTheme {
                val usuarioActivo by repositorioUsuario.usuarioActivo.collectAsState()
                val estaCargando by repositorioUsuario.cargando.collectAsState()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    ) {
                        when {
                            estaCargando -> {
                                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                            }
                            usuarioActivo != null -> {
                                PantallaPrincipal(
                                    usuario = usuarioActivo!!,
                                    usuarioRepository = repositorioUsuario
                                )
                            }
                            else -> {
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
