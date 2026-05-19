package com.example.dishy_app.ui.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dishy_app.data.model.Place
import com.example.dishy_app.data.repository.PlaceRepository
import kotlinx.coroutines.launch

class SavedPlacesViewModel : ViewModel() {
    private val repository = PlaceRepository()

    var savedPlaces by mutableStateOf<List<Place>>(emptyList())
        private set

    init {
        loadSavedPlaces()
    }

    private fun loadSavedPlaces() {
        viewModelScope.launch {
            // Por ahora, para probar la nube, traemos todos los lugares
            savedPlaces = repository.getAllPlaces()
        }
    }
}
