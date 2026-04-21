package com.example.dishy_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.LaunchedEffect
import com.example.dishy_app.ui.navigation.AppNavGraph
import com.example.dishy_app.ui.theme.DishyAppTheme
import kotlinx.coroutines.delay


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DishyAppTheme {
                val currentUser by FirebaseAuthManager.currentUser.collectAsState()
                var isLoading by remember { mutableStateOf(true) }

                LaunchedEffect(Unit) {
                    delay(500) 
                    isLoading = false
                }

                if (!isLoading) {
                    AppNavGraph(isUserLoggedIn = currentUser != null)
                }
            }
        }
    }
}
