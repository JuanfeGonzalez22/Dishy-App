package com.example.dishy_app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.dishy_app.ui.screens.*

@Composable
fun AppNavGraph(isUserLoggedIn: Boolean = false) {
    val navController = rememberNavController()

    // Determinamos la pantalla de inicio
    val startDestination = if (isUserLoggedIn) "home" else "login"

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // 1. Login
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

        // 2. Registro
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

        // 3. Olvidé mi contraseña
        composable("forgot_password") {
            ForgotPasswordScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // 4. Feed Social (Home)
        composable("home") {
            HomeSocialFeedScreen(navController = navController)
        }

        // 5. Shake & Discover
        composable("shake") {
            ShakeDiscoverScreen(navController = navController)
        }

        // 6. Mapa
        composable("map") {
            MapScreen(navController = navController)
        }

        // 7. Detalle de Restaurante (Datos Reales)
        composable(
            route = "detail/{placeId}",
            arguments = listOf(navArgument("placeId") { type = NavType.StringType })
        ) { backStackEntry ->
            val placeId = backStackEntry.arguments?.getString("placeId") ?: ""
            PlaceDetailScreen(placeId = placeId, navController = navController)
        }

        // 8. Lugares Guardados
        composable("saved_places") {
            SavedPlacesScreen(navController = navController)
        }

        // 9. Perfil de Usuario
        composable(
            route = "profile?userId={userId}",
            arguments = listOf(navArgument("userId") { 
                type = NavType.StringType
                nullable = true
                defaultValue = null
            })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")
            ProfileScreen(navController = navController, userId = userId)
        }

        // 10. Perfil de Dueño de Restaurante
        composable("restaurant_profile") {
            RestaurantProfileScreen(navController = navController)
        }

        // 11. Cámara
        composable("camera") {
            CameraScreen(navController = navController)
        }

        // 12. Creación de Post (Recibe la URI de la foto)
        composable(
            route = "create_post?imageUri={imageUri}",
            arguments = listOf(navArgument("imageUri") {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            })
        ) { backStackEntry ->
            val imageUri = backStackEntry.arguments?.getString("imageUri")
            CreatePostScreen(imageUri = imageUri, navController = navController)
        }

        // 13. Detalle de Post Social (Datos Reales)
        composable(
            route = "post_detail/{postId}",
            arguments = listOf(navArgument("postId") { type = NavType.StringType })
        ) { backStackEntry ->
            val postId = backStackEntry.arguments?.getString("postId") ?: ""
            PostDetailScreen(postId = postId, navController = navController)
        }

        // 14. Editar Perfil
        composable(
            route = "edit_profile/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            EditProfileScreen(userId = userId, navController = navController)
        }
    }
}
