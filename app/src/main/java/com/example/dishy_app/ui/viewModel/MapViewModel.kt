package com.example.dishy_app.ui.viewModel

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.dishy_app.data.model.Place
import com.example.dishy_app.data.repository.PlaceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MapViewModel(application: Application) : AndroidViewModel(application) {

    private val placeRepository = PlaceRepository()

    // Estado que le dice a la UI si tiene permiso o no
    private val _locationGranted = MutableStateFlow(false)
    val locationGranted: StateFlow<Boolean> = _locationGranted

    private var allPlaces = emptyList<Place>()
    var filteredPlaces by mutableStateOf<List<Place>>(emptyList())
        private set

    var searchQuery by mutableStateOf("")
        private set

    init {
        loadPlaces()
    }

    private fun loadPlaces() {
        viewModelScope.launch {
            allPlaces = placeRepository.getAllPlaces()
            filterPlaces()
        }
    }

    fun onSearchQueryChange(newQuery: String) {
        searchQuery = newQuery
        filterPlaces()
    }

    private fun filterPlaces() {
        filteredPlaces = if (searchQuery.isBlank()) {
            allPlaces
        } else {
            allPlaces.filter {
                it.name.contains(searchQuery, ignoreCase = true) ||
                it.category.contains(searchQuery, ignoreCase = true) ||
                it.description.contains(searchQuery, ignoreCase = true)
            }
        }
    }

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