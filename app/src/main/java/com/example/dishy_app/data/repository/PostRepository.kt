package com.example.dishy_app.data.repository

import com.example.dishy_app.data.model.DishyPost
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class PostRepository {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val postsCollection = db.collection("posts")
    private val usersCollection = db.collection("users")

    // Obtiene posts en tiempo real y mapea el ID del documento al objeto
    fun getPostsFlow(): Flow<List<DishyPost>> = callbackFlow {
        val subscription = postsCollection
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val posts = snapshot.documents.mapNotNull { doc ->
                        doc.toObject(DishyPost::class.java)?.copy(id = doc.id)
                    }
                    trySend(posts)
                }
            }
        awaitClose { subscription.remove() }
    }

    suspend fun getAllPosts(): List<DishyPost> {
        return try {
            val snapshot = postsCollection
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .await()
            snapshot.documents.mapNotNull { doc ->
                doc.toObject(DishyPost::class.java)?.copy(id = doc.id)
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun createPost(post: DishyPost): Boolean {
        return try {
            postsCollection.document(post.id).set(post).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun deletePost(postId: String): Boolean {
        return try {
            postsCollection.document(postId).delete().await()
            true
        } catch (e: Exception) {
            false
        }
    }

    // --- LÓGICA DE FAVORITOS (POSTS/VIBES) ---

    suspend fun toggleFavoritePost(postId: String): Boolean {
        val userId = auth.currentUser?.uid ?: return false
        val userRef = usersCollection.document(userId)
        
        return try {
            val doc = userRef.get().await()
            val favorites = doc.get("favoritePosts") as? List<String> ?: emptyList()
            
            if (favorites.contains(postId)) {
                userRef.update("favoritePosts", FieldValue.arrayRemove(postId)).await()
            } else {
                userRef.update("favoritePosts", FieldValue.arrayUnion(postId)).await()
            }
            true
        } catch (e: Exception) {
            false
        }
    }

    fun getFavoritePostIdsFlow(): Flow<List<String>> = callbackFlow {
        val userId = auth.currentUser?.uid
        if (userId == null) {
            trySend(emptyList())
            close()
            return@callbackFlow
        }

        val subscription = usersCollection.document(userId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                if (snapshot != null && snapshot.exists()) {
                    val favorites = snapshot.get("favoritePosts") as? List<String> ?: emptyList()
                    trySend(favorites)
                } else {
                    trySend(emptyList())
                }
            }
        awaitClose { subscription.remove() }
    }

    // --- LÓGICA DE FAVORITOS (LUGARES/PLACES) ---

    suspend fun toggleFavoritePlace(placeId: String): Boolean {
        val userId = auth.currentUser?.uid ?: return false
        val userRef = usersCollection.document(userId)
        
        return try {
            val doc = userRef.get().await()
            val favorites = doc.get("favoritePlaces") as? List<String> ?: emptyList()
            
            if (favorites.contains(placeId)) {
                userRef.update("favoritePlaces", FieldValue.arrayRemove(placeId)).await()
            } else {
                userRef.update("favoritePlaces", FieldValue.arrayUnion(placeId)).await()
            }
            true
        } catch (e: Exception) {
            false
        }
    }

    fun getFavoritePlaceIdsFlow(): Flow<List<String>> = callbackFlow {
        val userId = auth.currentUser?.uid
        if (userId == null) {
            trySend(emptyList())
            close()
            return@callbackFlow
        }

        val subscription = usersCollection.document(userId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                if (snapshot != null && snapshot.exists()) {
                    val favorites = snapshot.get("favoritePlaces") as? List<String> ?: emptyList()
                    trySend(favorites)
                } else {
                    trySend(emptyList())
                }
            }
        awaitClose { subscription.remove() }
    }
}
