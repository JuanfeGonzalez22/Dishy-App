package com.example.dishy_app.data.model

data class DishyPost(
    val id: String = "",
    val userId: String = "",
    val userName: String = "",
    val authorName: String = "",
    val authorPhotoUrl: String = "",
    val authorRole: String = "USER",
    val placeName: String = "",
    val location: String = "",
    val imageUrl: String = "",
    val description: String = "",
    val tags: List<String> = emptyList(),
    val timestamp: Long = System.currentTimeMillis(),
    val vibeSpecs: VibeSpecs = VibeSpecs()
)

data class VibeSpecs(
    val wifiSpeed: String = "Normal",
    val noiseLevel: String = "Medium",
    val comfortLevel: String = "Standard",
    val plugsAvailable: Boolean = false
)
