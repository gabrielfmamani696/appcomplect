package com.gabrieldev.appcomplect

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
        conn
    }

    private val repositorioUsuario by lazy { UsuarioRepository(connector, applicationContext) }
    private val repositorioAvatar by lazy { AvatarRepository(connector) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val factory = androidx.compose.runtime.remember { com.gabrieldev.appcomplect.ui.AppViewModelFactory(repositorioUsuario, repositorioAvatar) }
            val mainViewModel: com.gabrieldev.appcomplect.ui.MainViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = factory)

            androidx.compose.runtime.LaunchedEffect(Unit) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                    if (androidx.core.content.ContextCompat.checkSelfPermission(
                            this@MainActivity,
                            android.Manifest.permission.POST_NOTIFICATIONS
                        ) != android.content.pm.PackageManager.PERMISSION_GRANTED
                    ) {
                        androidx.core.app.ActivityCompat.requestPermissions(
                            this@MainActivity,
                            arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                            101
                        )
                    }
                }
            }

            AppcomplectTheme {
                val usuarioActivo by mainViewModel.usuarioActivo.collectAsState()
                val estaCargando by mainViewModel.estaCargando.collectAsState()

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
                                    usuarioRepository = repositorioUsuario,
                                    avatarRepository = repositorioAvatar
                                )
                            }
                            else -> {
                                val registroViewModel: com.gabrieldev.appcomplect.ui.RegistroViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = factory)
                                RegistroUsuario(
                                    viewModel = registroViewModel,
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
