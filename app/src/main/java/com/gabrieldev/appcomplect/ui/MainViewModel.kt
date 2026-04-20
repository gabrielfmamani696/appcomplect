package com.gabrieldev.appcomplect.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabrieldev.appcomplect.data.repository.UsuarioRepository
import com.gabrieldev.appcomplect.data.repository.InsigniaRepository
import com.gabrieldev.appcomplect.data.repository.ContextoInsignia
import com.gabrieldev.appcomplect.model.InsigniaObtenida
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainViewModel(
    private val usuarioRepository: UsuarioRepository,
    private val insigniaRepository: InsigniaRepository,
    private val context: Context
) : ViewModel() {

    val usuarioActivo = usuarioRepository.usuarioActivo
    val estaCargando = usuarioRepository.cargando

    private val _insigniasNuevas = MutableStateFlow<List<InsigniaObtenida>>(emptyList())
    val insigniasNuevas = _insigniasNuevas.asStateFlow()

    private val _insigniasObtenidas = MutableStateFlow<List<InsigniaObtenida>>(emptyList())
    val insigniasObtenidas = _insigniasObtenidas.asStateFlow()

    private val _enLinea = MutableStateFlow(true)
    val enLinea = _enLinea.asStateFlow()

    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    init {
        viewModelScope.launch {
            usuarioActivo.collectLatest { usuario ->
                val usuarioId = usuario?.uuidSesion
                if (usuarioId.isNullOrBlank()) {
                    _insigniasObtenidas.value = emptyList()
                } else {
                    val obtenidas = insigniaRepository.obtenerInsigniasObtenidas(usuarioId)
                    _insigniasObtenidas.value = obtenidas
                }
            }
        }
        verificarSesion()
        registrarConnectivityCallback()
    }

    private fun verificarSesion() {
        viewModelScope.launch {
            usuarioRepository.verificarSesion()
        }
    }

    private fun registrarConnectivityCallback() {
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        connectivityManager.registerNetworkCallback(networkRequest, object: ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                _enLinea.value = true
            }

            override fun onLost(network: Network) {
                _enLinea.value = false
            }
        })

        // Verificar estado inicial
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        _enLinea.value = capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }

    fun registrarAccionDiaria() {
        viewModelScope.launch {
            usuarioRepository.registrarAccionDiaria()
            val usuarioConRacha = usuarioRepository.usuarioActivo.value
            val uid = usuarioConRacha?.uuidSesion
            if (!uid.isNullOrBlank()) {
                val nuevas = insigniaRepository.verificarInsignias(
                    usuarioId = uid,
                    contexto = ContextoInsignia.Racha(
                        rachaActualDias = usuarioConRacha.rachaActualDias
                    )
                )
                if (nuevas.isNotEmpty()) _insigniasNuevas.value = nuevas
            }
        }
    }

    fun limpiarInsignias() {
        _insigniasNuevas.value = emptyList()
    }
}
