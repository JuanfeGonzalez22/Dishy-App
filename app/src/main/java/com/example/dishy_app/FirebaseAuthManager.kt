package com.example.dishy_app

import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.tasks.await

object FirebaseAuthManager {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _currentUser = MutableStateFlow<FirebaseUser?>(auth.currentUser)
    val currentUser: StateFlow<FirebaseUser?> = _currentUser.asStateFlow()

    init {
        auth.addAuthStateListener { firebaseAuth ->
            _currentUser.value = firebaseAuth.currentUser
            Log.d("FirebaseAuthManager", "Usuario actual: ${firebaseAuth.currentUser?.displayName ?: "No autenticado"}")
        }
    }

    suspend fun signInWithGoogle(context: Context): Result<FirebaseUser> {
        return try {
            val googleIdOption = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(true)  // <- CAMBIADO DE false A true
                .setServerClientId(context.getString(R.string.default_web_client_id))
                .build()

            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            val credentialManager = CredentialManager.create(context)

            try {
                val result = credentialManager.getCredential(
                    request = request,
                    context = context
                )
                processCredentialResult(result)
            } catch (e: NoCredentialException) {
                Log.d("FirebaseAuthManager", "No hay cuentas guardadas, mostrando selector completo")
                val googleIdOptionAll = GetGoogleIdOption.Builder()
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(context.getString(R.string.default_web_client_id))
                    .build()

                val requestAll = GetCredentialRequest.Builder()
                    .addCredentialOption(googleIdOptionAll)
                    .build()

                val result = credentialManager.getCredential(
                    request = requestAll,
                    context = context
                )
                processCredentialResult(result)
            }

        } catch (e: GetCredentialException) {
            Log.e("FirebaseAuthManager", "Error obteniendo credencial: ${e.message}", e)
            Result.failure(Exception("Error al obtener credencial: ${e.message}"))
        } catch (e: Exception) {
            Log.e("FirebaseAuthManager", "Error inesperado: ${e.message}", e)
            Result.failure(Exception("Error inesperado: ${e.message}"))
        }
    }

    private suspend fun processCredentialResult(result: androidx.credentials.GetCredentialResponse): Result<FirebaseUser> {
        return when (val credential = result.credential) {
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                        val firebaseUser = authenticateWithFirebase(googleIdTokenCredential.idToken)
                        Result.success(firebaseUser)
                    } catch (e: GoogleIdTokenParsingException) {
                        Log.e("FirebaseAuthManager", "Error parseando token de Google", e)
                        Result.failure(Exception("Error al procesar la cuenta de Google"))
                    }
                } else {
                    Result.failure(Exception("Tipo de credencial no válido"))
                }
            }
            else -> Result.failure(Exception("Credencial no reconocida"))
        }
    }

    private suspend fun authenticateWithFirebase(idToken: String): FirebaseUser {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        val authResult = auth.signInWithCredential(credential).await()
        return authResult.user ?: throw Exception("Error al obtener usuario de Firebase")
    }

    fun signOut() {
        auth.signOut()
        Log.d("FirebaseAuthManager", "Sesión cerrada")
    }

    // --- REGISTRO LOCAL ---
    suspend fun signUpWithEmail(email: String, pass: String): Result<FirebaseUser> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, pass).await()
            Result.success(result.user!!)
        } catch (e: Exception) {
            Log.e("FirebaseAuthManager", "Error en registro: ${e.message}")
            Result.failure(e)
        }
    }

    // --- LOGIN LOCAL ---
    suspend fun signInWithEmail(email: String, pass: String): Result<FirebaseUser> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, pass).await()
            Result.success(result.user!!)
        } catch (e: Exception) {
            Log.e("FirebaseAuthManager", "Error en login: ${e.message}")
            Result.failure(e)
        }
    }
}