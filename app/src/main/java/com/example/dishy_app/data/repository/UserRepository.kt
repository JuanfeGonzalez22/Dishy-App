package com.example.dishy_app.data.repository

import com.example.dishy_app.data.model.UserProfile
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserRepository {

    private val db = FirebaseFirestore.getInstance()

    suspend fun saveUser(user: UserProfile) {

        try {
            db.collection("users").document(user.id).set(user).await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun getUserRole(uid: String): String? {
        return try {
            val document = db.collection("users").document(uid).get().await()
            document.getString("role")
        } catch (e: Exception) {
            null
        }
    }
}
