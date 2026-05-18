package com.example.dishy_app.data.repository

import com.example.dishy_app.data.model.DishyPost
import com.example.dishy_app.data.model.VibeSpecs

class PostRepository {
    private val samplePosts = listOf(
        DishyPost(
            id = "1",
            userName = "Erick Sebastian",
            placeName = "La Parrilla de Juan",
            imageUrl = "https://images.unsplash.com/photo-1544025162-d76694265947",
            description = "Increíble asado, ¡el ambiente es 10/10!",
            vibeSpecs = VibeSpecs(wifiSpeed = "High Speed", noiseLevel = "Low", plugsAvailable = true)
        ),
        DishyPost(
            id = "2",
            userName = "Maria Garcia",
            placeName = "Sushi Master",
            imageUrl = "https://images.unsplash.com/photo-1579871494447-9811cf80d66c",
            description = "El mejor sushi de la zona. Muy recomendado.",
            vibeSpecs = VibeSpecs(wifiSpeed = "Medium", noiseLevel = "Medium", plugsAvailable = false)
        ),
        DishyPost(
            id = "3",
            userName = "Carlos Perez",
            placeName = "Café del Bosque",
            imageUrl = "https://images.unsplash.com/photo-1501339847302-ac426a4a7cbb",
            description = "Perfecto para trabajar un rato.",
            vibeSpecs = VibeSpecs(wifiSpeed = "High Speed", noiseLevel = "Low", plugsAvailable = true)
        )
    )

    fun getAllPosts(): List<DishyPost> = samplePosts
}
