package com.example.dishy_app.ui.viewModel

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.example.dishy_app.FirebaseAuthManager
import com.example.dishy_app.data.model.DishyPost
import com.example.dishy_app.data.model.VibeSpecs
import com.example.dishy_app.data.repository.PostRepository
import java.util.*
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class CreatePostViewModel(application: Application) : AndroidViewModel(application) {
    private val postRepository = PostRepository()
    private val defaultAvatar = "https://cdn-icons-png.flaticon.com/512/149/149071.png"

    var isUploading by mutableStateOf(false)
        private set

    init {
        try {
            val config = mapOf(
                "cloud_name" to "dmqsitnom",
                "secure" to true
            )
            MediaManager.init(application, config)
        } catch (e: Exception) {
            Log.d("DishyApp", "Cloudinary ya inicializado")
        }
    }

    suspend fun uploadAndCreatePost(
        imageUri: Uri,
        caption: String,
        placeName: String,
        location: String,
        category: String,
        rating: Double,
        wifi: String,
        comfort: String,
        noise: String,
        plugs: Boolean,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        isUploading = true
        try {
            val imageUrl = uploadToCloudinary(imageUri)
            
            if (imageUrl != null) {
                val currentUser = FirebaseAuthManager.currentUser.value
                val photoUrl = currentUser?.photoUrl?.toString()
                
                val newPost = DishyPost(
                    id = UUID.randomUUID().toString(),
                    userId = currentUser?.uid ?: "",
                    userName = currentUser?.displayName ?: "Anonymous",
                    authorName = FirebaseAuthManager.userName.value ?: "Anonymous",
                    authorPhotoUrl = if (photoUrl.isNullOrBlank()) defaultAvatar else photoUrl,
                    authorRole = FirebaseAuthManager.userRole.value ?: "USER",
                    imageUrl = imageUrl,
                    description = caption,
                    placeName = placeName,
                    location = location,
                    category = category,
                    rating = rating,
                    timestamp = System.currentTimeMillis(),
                    vibeSpecs = VibeSpecs(
                        wifiSpeed = wifi,
                        noiseLevel = noise,
                        comfortLevel = comfort,
                        plugsAvailable = plugs
                    )
                )

                val success = postRepository.createPost(newPost)
                if (success) onSuccess() else onError("Error saving metadata")
            } else {
                onError("Cloudinary upload failed")
            }
        } catch (e: Exception) {
            onError("Error: ${e.message}")
        } finally {
            isUploading = false
        }
    }

    private suspend fun uploadToCloudinary(uri: Uri): String? = suspendCoroutine { continuation ->
        MediaManager.get().upload(uri)
            .unsigned("ProjectDISHYApp")
            .callback(object : UploadCallback {
                override fun onStart(requestId: String) {}
                override fun onProgress(requestId: String, bytes: Long, totalBytes: Long) {}
                override fun onSuccess(requestId: String, resultData: Map<*, *>) {
                    continuation.resume(resultData["secure_url"] as? String)
                }
                override fun onError(requestId: String, error: ErrorInfo) {
                    Log.e("DishyApp", "Cloudinary Error: ${error.description}")
                    continuation.resume(null)
                }
                override fun onReschedule(requestId: String, error: ErrorInfo) {}
            }).dispatch()
    }
}
