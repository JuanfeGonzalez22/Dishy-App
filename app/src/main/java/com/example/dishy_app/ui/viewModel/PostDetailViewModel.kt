package com.example.dishy_app.ui.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dishy_app.data.model.DishyPost
import com.example.dishy_app.data.repository.PostRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class PostDetailViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val postRepository = PostRepository()
    
    var post by mutableStateOf<DishyPost?>(null)
        private set

    var isFavorite by mutableStateOf(false)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var isDeleting by mutableStateOf(false)
        private set

    fun loadPost(postId: String) {
        viewModelScope.launch {
            isLoading = true
            try {
                val document = db.collection("posts").document(postId).get().await()
                post = document.toObject(DishyPost::class.java)?.copy(id = document.id)
                
                // Observar si es favorito
                observeFavoriteStatus(postId)
            } catch (e: Exception) {
                post = null
            } finally {
                isLoading = false
            }
        }
    }

    private fun observeFavoriteStatus(postId: String) {
        viewModelScope.launch {
            postRepository.getFavoritePostIdsFlow().collect { favoriteIds ->
                isFavorite = favoriteIds.contains(postId)
            }
        }
    }

    fun toggleFavorite() {
        val postId = post?.id ?: return
        viewModelScope.launch {
            postRepository.toggleFavoritePost(postId)
        }
    }

    fun deletePost(postId: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            isDeleting = true
            val success = postRepository.deletePost(postId)
            if (success) {
                onSuccess()
            }
            isDeleting = false
        }
    }
}
