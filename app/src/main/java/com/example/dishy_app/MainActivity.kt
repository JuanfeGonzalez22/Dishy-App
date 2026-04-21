package com.example.dishy_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.dishy_app.ui.navigation.AppNavGraph
import com.example.dishy_app.ui.theme.DishyAppTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DishyAppTheme {
                val currentUser by FirebaseAuthManager.currentUser.collectAsState()
                AppNavGraph(isUserLoggedIn = currentUser != null)
            }
        }
    }
}
