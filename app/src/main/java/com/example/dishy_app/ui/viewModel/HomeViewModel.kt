package com.example.dishy_app.ui.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dishy_app.data.model.DishyPost
import com.example.dishy_app.data.model.Place
import com.example.dishy_app.data.repository.PlaceRepository
import com.example.dishy_app.data.repository.PostRepository
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val placeRepository = PlaceRepository()
    private val postRepository = PostRepository()

    var places by mutableStateOf<List<Place>>(emptyList())
        private set

    var posts by mutableStateOf<List<DishyPost>>(emptyList())
        private set

    // Estado para saber si estamos cargando datos
    var isLoading by mutableStateOf(false)
        private set

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            isLoading = true
            // Lanzamos ambas peticiones en paralelo o secuencial
            places = placeRepository.getAllPlaces()
            posts = postRepository.getAllPosts()
            isLoading = false
        }
    }
}
