package com.example.dishy_app.ui.viewModel


import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class RegisterViewModel : ViewModel() {

    var fullName by mutableStateOf("")
        private set
    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set

    fun onFullNameChange(newValue: String) {
        fullName = newValue
    }

    fun onEmailChange(newValue: String) {
        email = newValue
    }

    fun onPasswordChange(newValue: String) {
        password = newValue
    }

    fun onRegisterClick() {
        if (fullName.isNotEmpty() && email.isNotEmpty() && password.length >= 8) {
            println("Registrando a: $fullName con el correo: $email")
        } else {
            println("Error: Por favor completa todos los campos correctamente.")
        }
    }
}