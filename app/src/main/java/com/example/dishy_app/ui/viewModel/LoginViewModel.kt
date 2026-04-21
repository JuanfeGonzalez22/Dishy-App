package com.example.dishy_app.ui.viewModel


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dishy_app.FirebaseAuthManager
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)

    fun onEmailChange(newValue: String) {
        email = newValue
    }

    fun onPasswordChange(newValue: String) {
        password = newValue
    }

    fun onLoginClick(onSuccess: () -> Unit) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            viewModelScope.launch {
                isLoading = true
                errorMessage = null
                val result = FirebaseAuthManager.signInWithEmail(email, password)
                isLoading = false
                
                result.onSuccess {
                    onSuccess()
                }.onFailure { e ->
                    errorMessage = e.message
                }
            }
        } else {
            errorMessage = "Please fill in all fields"
        }
    }
}