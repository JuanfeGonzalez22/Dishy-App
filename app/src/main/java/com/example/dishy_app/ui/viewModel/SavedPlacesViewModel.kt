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

class SavedPlacesViewModel : ViewModel() {
    private val placeRepository = PlaceRepository()
    private val postRepository = PostRepository()

    var savedPlaces by mutableStateOf<List<Place>>(emptyList())
        private set
        
    var savedVibes by mutableStateOf<List<DishyPost>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            isLoading = true
            // 1. Cargar lugares guardados (actualmente trae todos para demo)
            savedPlaces = placeRepository.getAllPlaces()
            
            // 2. Cargar IDs de posts favoritos y luego sus datos
            postRepository.getFavoritePostIdsFlow().collect { favoriteIds ->
                val allPosts = postRepository.getAllPosts()
                savedVibes = allPosts.filter { favoriteIds.contains(it.id) }
                isLoading = false
            }
        }
    }
}
