package com.example.dishy_app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.dishy_app.ui.screens.ForgotPasswordScreen
import com.example.dishy_app.ui.screens.HomeSocialFeedScreen
import com.example.dishy_app.ui.screens.LoginScreen
import com.example.dishy_app.ui.screens.MapScreen
import com.example.dishy_app.ui.screens.PlaceDetailScreen
import com.example.dishy_app.ui.screens.RegisterScreen
import com.example.dishy_app.ui.screens.SavedPlacesScreen
import com.example.dishy_app.ui.screens.ShakeDiscoverScreen
import com.example.dishy_app.ui.screens.samplePlaces

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        // 1. Pantalla de Login
        composable("login") {
            LoginScreen(
                onNavigateToRegister = { navController.navigate("register") },
                onNavigateToHome = { navController.navigate("home") },
                onNavigateToForgotPassword = { navController.navigate("forgot_password") }
            )
        }

        // 2. Pantalla de Registro
        composable("register") {
            RegisterScreen(
                onNavigateToLogin = { navController.navigate("login") },
                onNavigateToHome = { navController.navigate("home") }
            )
        }

        // 3. Pantalla de Recuperar Contraseña
        composable("forgot_password") {
            ForgotPasswordScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // 4. Pantalla de Home
        composable("home") {
            HomeSocialFeedScreen(navController = navController)
        }

        // 5. Pantalla de Shake
        composable("shake") {
            ShakeDiscoverScreen(navController = navController)
        }

        // 6. Pantalla de Map
        composable("map") {
            MapScreen(navController = navController)
        }

        // 7. Pantalla de Detalles del Lugar
        composable(
            route = "detail/{placeId}",
            arguments = listOf(navArgument("placeId") { type = NavType.IntType })
        ) { backStackEntry ->
            val placeId = backStackEntry.arguments?.getInt("placeId") ?: 1
            val place = samplePlaces.find { it.id == placeId }
            if (place != null) {
                PlaceDetailScreen(place = place, navController = navController)
            }
        }

        // 8. Pantalla de Saved Places
        composable("saved_places") {
            SavedPlacesScreen(navController = navController)
        }
    }
}