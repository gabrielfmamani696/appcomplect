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
import com.gabrieldev.appcomplect.data.repository.AvatarRepository
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

    /**
     * dependencia para la conexion con DataStore
     */
    private val connector by lazy {
        DefaultConnector.instance
    }

    /**
     *  mediante los repositoris, se obtienen los datos, funcionando como un intermedairio que maneja los datos de la app
    */
    private val repositorioUsuario by lazy { UsuarioRepository(connector, applicationContext) }
    private val repositorioAvatar by lazy { AvatarRepository(connector) }

    /**
     * ejecucion al abrir la app
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /**
         * la ui ocupa toda la pantalla, incluso la barra superior del disp.
         */
        enableEdgeToEdge()

        /**
         * inicio de JC para mostrar la ui
         */
        setContent {

            /**
             * Los repositorios se guardan y se mantienen incluso si
             * la ui se recompone, para eso utilizo remember
             */
            val factory = remember {
                AppViewModelFactory(
                repositorioUsuario,
                repositorioAvatar
                )
            }

            /**
             * la varaible de tipo ViewModel, contiene la conexion de la app, en este caso para verificar
             * la sesion y no perder la conexion pese a la recomposicion
             */
            val mainViewModel: MainViewModel = viewModel(factory = factory)

            /**
             * LE ejecuta codigo asincrono, ejecutandose en paralelo, o por detras,
             * la primera vez que se carga la ui
             */
            LaunchedEffect(Unit) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    // no se ha otorgado el permiso?
                    if (ContextCompat.checkSelfPermission(
                            this@MainActivity,
                            Manifest.permission.POST_NOTIFICATIONS
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {

                        // entonces se solicita el permiso
                        ActivityCompat.requestPermissions(
                            this@MainActivity,
                            arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                            101
                        )
                    }
                }
            }

            /**
             * Aplica la paleta de colores, tipografías y formas globales de la aplicacion.
             */
            AppcomplectTheme {

                /**
                 * Variables reactivas al estado actual de las var
                 * usuarioActivo de viewmodel y estaCargando
                 */
                val usuarioActivo by mainViewModel.usuarioActivo.collectAsState()
                val estaCargando by mainViewModel.estaCargando.collectAsState()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    ) {
                        /**
                         * Cuando estaCargando es True, o usuarioActivo != null
                         */
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
