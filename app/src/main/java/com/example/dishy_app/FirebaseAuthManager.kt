package com.example.dishy_app

import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.NoCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

object FirebaseAuthManager {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance() // Instancia de Firestore

    private val _currentUser = MutableStateFlow(auth.currentUser)
    val currentUser: StateFlow<FirebaseUser?> = _currentUser.asStateFlow()

    private val _userRole = MutableStateFlow<String?>(null)
    val userRole: StateFlow<String?> = _userRole.asStateFlow()

    private val _userName = MutableStateFlow<String?>(null)
    val userName: StateFlow<String?> = _userName.asStateFlow()

    init {
        auth.addAuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            _currentUser.value = user
            if (user != null) {
                // Al detectar usuario, cargamos sus datos de Firestore
                kotlinx.coroutines.MainScope().launch {
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

    // --- OBTENER DATOS COMPLETOS DESDE FIRESTORE ---
    private suspend fun getUserData(uid: String): Map<String, Any> {
        return try {
            val document = db.collection("users").document(uid).get().await()
            document.data ?: emptyMap()
        } catch (e: Exception) {
            Log.e("FirebaseAuthManager", "Error obteniendo datos: ${e.message}")
            emptyMap()
        }
    }

    // --- OBTENER ROL DESDE FIRESTORE (Mantener por compatibilidad si se usa fuera) ---
    suspend fun getUserRole(uid: String): String {
        return getUserData(uid)["role"] as? String ?: "USER"
    }

    // --- REGISTRO CON ROL ---
    suspend fun signUpWithEmail(
        email: String,
        pass: String,
        name: String,
        isBusiness: Boolean
    ): Result<FirebaseUser> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, pass).await()
            val user = result.user!!

            // Actualizar el perfil en Firebase Auth para que displayName no sea null
            val profileUpdates = userProfileChangeRequest {
                displayName = name
            }
            user.updateProfile(profileUpdates).await()

            // Crear el perfil en Firestore
            val userData = hashMapOf(
                "uid" to user.uid,
                "name" to name,
                "email" to email,
                "role" to if (isBusiness) "BUSINESS" else "USER",
                "createdAt" to System.currentTimeMillis()
            )

            db.collection("users").document(user.uid).set(userData).await()
            
            // Forzar actualización del StateFlow
            _currentUser.value = auth.currentUser

            Result.success(user)
        } catch (e: Exception) {
            Log.e("FirebaseAuthManager", "Error en registro: ${e.message}")
            Result.failure(e)
        }
    }

    // --- LOGIN TRADICIONAL ---
    suspend fun signInWithEmail(email: String, pass: String): Result<FirebaseUser> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, pass).await()
            Result.success(result.user!!)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // --- GOOGLE SIGN IN (Simplificado para el ejemplo) ---
    suspend fun signInWithGoogle(context: Context): Result<FirebaseUser> {
        return try {
            val googleIdOption = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(true)
                .setServerClientId(context.getString(R.string.default_web_client_id))
                .build()

            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            val result = CredentialManager.create(context).getCredential(context, request)
            val credential = result.credential as CustomCredential
            val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)

            val firebaseUser = authenticateWithFirebase(googleIdTokenCredential.idToken)

            // Verificar si el usuario de Google ya existe en Firestore, si no, crearlo como USER
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