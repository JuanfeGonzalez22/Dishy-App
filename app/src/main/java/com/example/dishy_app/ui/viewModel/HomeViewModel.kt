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

    private var allPlaces by mutableStateOf<List<Place>>(emptyList())
    private var allPosts by mutableStateOf<List<DishyPost>>(emptyList())

    var places by mutableStateOf<List<Place>>(emptyList())
        private set

    var posts by mutableStateOf<List<DishyPost>>(emptyList())
        private set

    var favoritePostIds by mutableStateOf<List<String>>(emptyList())
        private set

    var favoritePlaceIds by mutableStateOf<List<String>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var searchQuery by mutableStateOf("")
        private set

    init {
        loadData()
        observePosts()
        observeFavorites()
    }

    private fun observePosts() {
        viewModelScope.launch {
            postRepository.getPostsFlow().collect { updatedPosts ->
                allPosts = updatedPosts
                filterData()
            }
        }
    }

    private fun observeFavorites() {
        viewModelScope.launch {
            postRepository.getFavoritePostIdsFlow().collect { ids ->
                favoritePostIds = ids
            }
        }
        viewModelScope.launch {
            postRepository.getFavoritePlaceIdsFlow().collect { ids ->
                favoritePlaceIds = ids
            }
        }
    }

    fun loadData() {
        viewModelScope.launch {
            isLoading = true
            allPlaces = placeRepository.getAllPlaces()
            filterData()
            isLoading = false
        }
    }

    fun onSearchQueryChange(newQuery: String) {
        searchQuery = newQuery
        filterData()
    }

    private fun filterData() {
        posts = if (searchQuery.isBlank()) {
            allPosts
        } else {
            allPosts.filter { 
                it.placeName.contains(searchQuery, ignoreCase = true) || 
                it.description.contains(searchQuery, ignoreCase = true) ||
                it.location.contains(searchQuery, ignoreCase = true) ||
                it.category.contains(searchQuery, ignoreCase = true)
            }
        }

        places = if (searchQuery.isBlank()) {
            allPlaces
        } else {
            allPlaces.filter {
                it.name.contains(searchQuery, ignoreCase = true) ||
                it.description.contains(searchQuery, ignoreCase = true) ||
                it.category.contains(searchQuery, ignoreCase = true) ||
                it.address.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    fun toggleFavoritePost(postId: String) {
        viewModelScope.launch {
            postRepository.toggleFavoritePost(postId)
        }
    }

    fun toggleFavoritePlace(placeId: String) {
        viewModelScope.launch {
            postRepository.toggleFavoritePlace(placeId)
        }
    }
}
