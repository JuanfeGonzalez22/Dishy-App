package com.example.dishy_app.ui.viewModel

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MapViewModel(application: Application) : AndroidViewModel(application) {

    // Estado que le dice a la UI si tiene permiso o no
    private val _locationGranted = MutableStateFlow(false)
    val locationGranted: StateFlow<Boolean> = _locationGranted

    // Verifica si ya tiene el permiso de ubicacion
    fun checkLocationPermission() {
        val granted = ContextCompat.checkSelfPermission(
            getApplication(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        _locationGranted.value = granted
    }

    // Actualiza el estado cuando el usuario responde el dialogo
    fun onPermissionResult(isGranted: Boolean) {
        _locationGranted.value = isGranted
    }
}