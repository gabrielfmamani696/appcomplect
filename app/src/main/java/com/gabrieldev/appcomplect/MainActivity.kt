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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gabrieldev.appcomplect.data.local.AppComplectDatabase
import com.gabrieldev.appcomplect.data.repository.ArchivoRepository
import com.gabrieldev.appcomplect.data.repository.AvatarRepository
import com.gabrieldev.appcomplect.data.repository.InsigniaRepository
import com.gabrieldev.appcomplect.data.repository.SyncRepository
import com.gabrieldev.appcomplect.data.repository.UsuarioRepository
import com.gabrieldev.appcomplect.ui.AppViewModelFactory
import com.gabrieldev.appcomplect.ui.MainViewModel
import com.gabrieldev.appcomplect.ui.RegistroUsuario
import com.gabrieldev.appcomplect.ui.RegistroViewModel
import com.gabrieldev.appcomplect.ui.principal.PantallaPrincipal
import com.gabrieldev.appcomplect.ui.theme.AppcomplectTheme
import com.google.firebase.dataconnect.generated.DefaultConnector
import com.google.firebase.dataconnect.generated.instance

class MainActivity : ComponentActivity() {

    private val connector by lazy {
        DefaultConnector.instance
    }

    private val db by lazy { AppComplectDatabase.getInstance(applicationContext) }
    private val repositorioUsuario by lazy { UsuarioRepository(connector, applicationContext, db.usuarioDao(), db.intentoDao()) }
    private val repositorioAvatar by lazy { AvatarRepository(connector) }
    private val repositorioArchivo by lazy { ArchivoRepository(connector, applicationContext, db.archivoDao(), db.descargaDao()) }
    private val repositorioInsignia by lazy { InsigniaRepository(connector) }
    private val repositorioSync by lazy { SyncRepository(db.usuarioDao(), db.intentoDao(), connector, repositorioInsignia) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {

            val factory = remember {
                AppViewModelFactory(
                    repositorioUsuario,
                    repositorioAvatar,
                    repositorioArchivo,
                    repositorioInsignia,
                    repositorioSync,
                    applicationContext
                )
            }

            val mainViewModel: MainViewModel = viewModel(factory = factory)

            LaunchedEffect(Unit) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    if (ContextCompat.checkSelfPermission(
                            this@MainActivity,
                            Manifest.permission.POST_NOTIFICATIONS
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        ActivityCompat.requestPermissions(
                            this@MainActivity,
                            arrayOf(Manifest.permission.POST_NOTIFICATIONS),
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
                                    avatarRepository = repositorioAvatar,
                                    archivoRepository = repositorioArchivo,
                                    insigniaRepository = repositorioInsignia,
                                    syncRepository = repositorioSync,
                                    mainViewModel = mainViewModel
                                )
                            }
                            else -> {
                                val registroViewModel: RegistroViewModel = viewModel(factory = factory)
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
