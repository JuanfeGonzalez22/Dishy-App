package com.example.dishy_app.ui.viewModel

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.dishy_app.FirebaseAuthManager
import com.example.dishy_app.data.model.DishyPost
import com.example.dishy_app.data.model.VibeSpecs
import com.example.dishy_app.data.repository.PostRepository
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.util.*

class CreatePostViewModel : ViewModel() {
    private val postRepository = PostRepository()
    private val storage = FirebaseStorage.getInstance()

    var isUploading by mutableStateOf(false)
        private set

    suspend fun uploadAndCreatePost(
        imageUri: Uri,
        caption: String,
        wifi: String,
        comfort: String,
        noise: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        isUploading = true
        try {
            // 1. Generar nombre de archivo único
            val fileName = "posts/${UUID.randomUUID()}.jpg"
            val storageRef = storage.reference.child(fileName)
            
            Log.d("DishyApp", "Iniciando subida a Firebase: $fileName")

            // 2. Subir archivo
            // Usamos await() para esperar a que termine la subida real
            val uploadTask = storageRef.putFile(imageUri).await()
            
            // 3. Verificar si la subida fue exitosa
            if (uploadTask.task.isSuccessful) {
                Log.d("DishyApp", "Subida completada con éxito")
                
                // 4. Intentar obtener el link de descarga
                val downloadUrl = storageRef.downloadUrl.await().toString()
                Log.d("DishyApp", "URL generada: $downloadUrl")

                // 5. Guardar metadatos en Firestore
                val currentUser = FirebaseAuthManager.currentUser.value
                val newPost = DishyPost(
                    id = UUID.randomUUID().toString(),
                    userId = currentUser?.uid ?: "",
                    userName = currentUser?.displayName ?: "Anonymous",
                    authorName = FirebaseAuthManager.userName.value ?: "Anonymous",
                    authorPhotoUrl = currentUser?.photoUrl?.toString() ?: "",
                    authorRole = FirebaseAuthManager.userRole.value ?: "USER",
                    imageUrl = downloadUrl,
                    description = caption,
                    placeName = "Unknown Place", 
                    location = "Armenia, Quindío",
                    timestamp = System.currentTimeMillis(),
                    vibeSpecs = VibeSpecs(
                        wifiSpeed = wifi,
                        noiseLevel = noise,
                        comfortLevel = comfort
                    )
                )

                val success = postRepository.createPost(newPost)
                if (success) {
                    onSuccess()
                } else {
                    onError("Metadata save failed")
                }
            } else {
                onError("Upload task was not successful")
            }
        } catch (e: Exception) {
            Log.e("DishyApp", "Error fatal: ${e.message}")
            val msg = e.localizedMessage ?: ""
            if (msg.contains("Permission denied", ignoreCase = true)) {
                onError("Error de Permisos: Revisa las reglas de STORAGE en Firebase Console")
            } else if (msg.contains("Object does not exist", ignoreCase = true)) {
                onError("Error 404: El archivo no se subió. Revisa tu conexión o reglas de Storage")
            } else {
                onError("Error: $msg")
            }
        } finally {
            isUploading = false
        }
    }
}
