package com.example.dishy_app.ui.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.dishy_app.data.model.Place
import com.example.dishy_app.data.repository.PlaceRepository

class SavedPlacesViewModel : ViewModel() {
    private val repository = PlaceRepository()

    // Por ahora mostramos todos los lugares como "guardados" para probar la UI
    var savedPlaces by mutableStateOf<List<Place>>(emptyList())
        private set

    init {
        loadSavedPlaces()
    }

    private fun loadSavedPlaces() {
        savedPlaces = repository.getAllPlaces()
    }
}
