package com.example.dishy_app

import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import com.example.dishy_app.R

object FirebaseAuthManager {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    // Especificamos el tipo explícitamente para evitar errores de compilación
    private val _currentUser = MutableStateFlow<FirebaseUser?>(auth.currentUser)
    val currentUser: StateFlow<FirebaseUser?> = _currentUser.asStateFlow()

    private val _userRole = MutableStateFlow<String?>(null)
    val userRole: StateFlow<String?> = _userRole.asStateFlow()

    private val _userName = MutableStateFlow<String?>(null)
    val userName: StateFlow<String?> = _userName.asStateFlow()

    private val scope = MainScope()

    init {
        auth.addAuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            _currentUser.value = user
            if (user != null) {
                scope.launch {
                    val userData = getUserData(user.uid)
                    _userRole.value = userData["role"] as? String ?: "USER"
                    _userName.value = userData["name"] as? String ?: user.displayName
                }
            } else {
                _userRole.value = null
                _userName.value = null
            }
        }
    }

    private suspend fun getUserData(uid: String): Map<String, Any> {
        return try {
            val document = db.collection("users").document(uid).get().await()
            document.data ?: emptyMap()
        } catch (e: Exception) {
            Log.e("FirebaseAuthManager", "Error obteniendo datos: ${e.message}")
            emptyMap()
        }
    }

    suspend fun signUpWithEmail(
        email: String,
        pass: String,
        name: String,
        isBusiness: Boolean
    ): Result<FirebaseUser> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, pass).await()
            val user = result.user!!

            val profileUpdates = userProfileChangeRequest {
                displayName = name
            }
            user.updateProfile(profileUpdates).await()

            val userData = hashMapOf(
                "uid" to user.uid,
                "name" to name,
                "email" to email,
                "role" to if (isBusiness) "BUSINESS" else "USER",
                "createdAt" to System.currentTimeMillis()
            )

            db.collection("users").document(user.uid).set(userData).await()
            _currentUser.value = auth.currentUser

            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun signInWithEmail(email: String, pass: String): Result<FirebaseUser> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, pass).await()
            Result.success(result.user!!)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun signInWithGoogle(context: Context): Result<FirebaseUser> {
        return try {
            val googleIdOption = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(context.getString(R.string.default_web_client_id))
                .build()

            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            val result = CredentialManager.create(context).getCredential(context, request)
            val credential = result.credential as CustomCredential
            val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)

            val firebaseUser = authenticateWithFirebase(googleIdTokenCredential.idToken)

            val doc = db.collection("users").document(firebaseUser.uid).get().await()
            if (!doc.exists()) {
                val userData = hashMapOf(
                    "uid" to firebaseUser.uid,
                    "name" to firebaseUser.displayName,
                    "email" to firebaseUser.email,
                    "role" to "USER"
                )
                db.collection("users").document(firebaseUser.uid).set(userData).await()
            }

            Result.success(firebaseUser)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private suspend fun authenticateWithFirebase(idToken: String): FirebaseUser {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        return auth.signInWithCredential(credential).await().user!!
    }

    fun signOut() {
        auth.signOut()
    }
}
