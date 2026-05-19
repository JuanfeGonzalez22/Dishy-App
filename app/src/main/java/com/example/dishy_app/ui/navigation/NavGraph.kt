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
import com.example.dishy_app.ui.screens.PlaceDetailScreen
import com.example.dishy_app.ui.screens.RegisterScreen
import com.example.dishy_app.ui.screens.SavedPlacesScreen

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(
                onNavigateToRegister = { navController.navigate("register") },
                onNavigateToHome = { 
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onNavigateToForgotPassword = { navController.navigate("forgot_password") }
            )
        }

        composable("register") {
            RegisterScreen(
                onNavigateToLogin = { navController.navigate("login") },
                onNavigateToHome = { 
                    navController.navigate("home") {
                        popUpTo("register") { inclusive = true }
                    }
                }
            )
        }

        composable("home") {
            HomeSocialFeedScreen(navController = navController)
        }

        composable(
            route = "detail/{placeId}",
            arguments = listOf(navArgument("placeId") { type = NavType.StringType })
        ) { backStackEntry ->
            val placeId = backStackEntry.arguments?.getString("placeId") ?: ""
            PlaceDetailScreen(placeId = placeId, navController = navController)
        }

        composable("forgot_password") {
            ForgotPasswordScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable("saved_places") {
            SavedPlacesScreen(navController = navController)
        }
    }
}
