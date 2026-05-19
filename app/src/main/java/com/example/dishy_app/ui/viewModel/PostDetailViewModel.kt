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
    
    var post by mutableStateOf<DishyPost?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    fun loadPost(postId: String) {
        viewModelScope.launch {
            isLoading = true
            try {
                val document = db.collection("posts").document(postId).get().await()
                post = document.toObject(DishyPost::class.java)
            } catch (e: Exception) {
                post = null
            } finally {
                isLoading = false
            }
        }
    }
}
