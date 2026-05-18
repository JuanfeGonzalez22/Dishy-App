package com.example.dishy_app.data.model

data class DishyPost(
    val id: String = "",
    val userId: String = "",
    val userName: String = "",
    val placeName: String = "",
    val imageUrl: String = "",
    val description: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val vibeSpecs: VibeSpecs = VibeSpecs()
)

data class VibeSpecs(
    val wifiSpeed: String = "Normal",
    val noiseLevel: String = "Medium",
    val plugsAvailable: Boolean = false
)
