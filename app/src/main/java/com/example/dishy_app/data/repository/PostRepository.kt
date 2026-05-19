package com.example.dishy_app.data.repository

import com.example.dishy_app.data.model.DishyPost
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

class PostRepository {
    private val db = FirebaseFirestore.getInstance()
    private val postsCollection = db.collection("posts")

    // Obtiene todos los posts desde Firestore, ordenados por fecha
    suspend fun getAllPosts(): List<DishyPost> {
        return try {
            val snapshot = postsCollection
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .await()
            snapshot.toObjects(DishyPost::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    // Función para crear un nuevo post en Firebase
    suspend fun createPost(post: DishyPost): Boolean {
        return try {
            postsCollection.add(post).await()
            true
        } catch (e: Exception) {
            false
        }
    }
}
