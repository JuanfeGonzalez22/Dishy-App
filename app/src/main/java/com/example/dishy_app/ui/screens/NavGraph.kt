package com.example.dishy_app.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun AppNavGraph(){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"

    ){
        //Pantalla principal
        composable("home"){
            HomeSocialFeedScreen(navController = navController)
        }

        //Pantalla de detalles
        composable(
            route = "detail/{placeId}",
            arguments = listOf(navArgument("placeId"){type = NavType.IntType})

        ){backStackEntry ->
            val placeId = backStackEntry.arguments?.getInt("placeId") ?: 1
            val place = samplePlaces.find { it.id == placeId }
            if (place != null) {
                PlaceDetailScreen(place = place, navController = navController)
            }

        }
    }
}