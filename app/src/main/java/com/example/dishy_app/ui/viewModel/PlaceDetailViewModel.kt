package com.example.dishy_app.ui.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.dishy_app.data.model.Place
import com.example.dishy_app.data.repository.PlaceRepository

class PlaceDetailViewModel : ViewModel() {
    private val repository = PlaceRepository()

    var place by mutableStateOf<Place?>(null)
        private set

    fun loadPlace(id: Int) {
        place = repository.getPlaceById(id)
    }
}
