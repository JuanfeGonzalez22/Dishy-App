package com.example.dishy_app.ui.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dishy_app.FirebaseAuthManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class EditProfileViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    var name by mutableStateOf("")
    var email by mutableStateOf("")
    var role by mutableStateOf("")
    var bio by mutableStateOf("")
    var location by mutableStateOf("")
    var photoUrl by mutableStateOf("")
    
    var isLoading by mutableStateOf(false)
        private set
    
    var isSaving by mutableStateOf(false)
        private set

    // Rol del usuario que está REALIZANDO la edición (para permisos de Admin)
    var currentUserRole by mutableStateOf("USER")

    fun loadUserProfile(targetUserId: String) {
        viewModelScope.launch {
            isLoading = true
            try {
                // 1. Cargar datos del usuario objetivo
                val document = db.collection("users").document(targetUserId).get().await()
                if (document.exists()) {
                    name = document.getString("name") ?: ""
                    email = document.getString("email") ?: ""
                    role = document.getString("role") ?: "USER"
                    bio = document.getString("bio") ?: ""
                    location = document.getString("location") ?: ""
                }
                
                // 2. Cargar rol del usuario logueado actualmente (para ver si es Admin)
                val currentUid = auth.currentUser?.uid
                if (currentUid != null) {
                    val currentDoc = db.collection("users").document(currentUid).get().await()
                    currentUserRole = currentDoc.getString("role") ?: "USER"
                }
                
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading = false
            }
        }
    }

    fun saveProfile(targetUserId: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            isSaving = true
            try {
                val updates = mutableMapOf<String, Any>(
                    "name" to name,
                    "bio" to bio,
                    "location" to location
                )
                
                // Solo el Admin puede cambiar roles
                if (currentUserRole == "ADMIN") {
                    updates["role"] = role
                }
                
                db.collection("users").document(targetUserId).update(updates).await()
                
                // Si el usuario se edita a sí mismo, actualizar Firebase Auth
                if (targetUserId == auth.currentUser?.uid) {
                    val profileUpdates = com.google.firebase.auth.userProfileChangeRequest {
                        displayName = name
                    }
                    auth.currentUser?.updateProfile(profileUpdates)?.await()
                }
                
                onSuccess()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isSaving = false
            }
        }
    }
}
