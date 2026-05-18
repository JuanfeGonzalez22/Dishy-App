package com.example.dishy_app.ui.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.dishy_app.data.model.DishyPost
import com.example.dishy_app.data.model.Place
import com.example.dishy_app.data.repository.PlaceRepository
import com.example.dishy_app.data.repository.PostRepository

class HomeViewModel : ViewModel() {

    private val placeRepository = PlaceRepository()
    private val postRepository = PostRepository()

    // Estado que contiene la lista de lugares
    var places by mutableStateOf<List<Place>>(emptyList())
        private set

    // Estado que contiene la lista de posts
    var posts by mutableStateOf<List<DishyPost>>(emptyList())
        private set

    init {
        loadData()
    }

    private fun loadData() {
        places = placeRepository.getAllPlaces()
        posts = postRepository.getAllPosts()
    }
}
