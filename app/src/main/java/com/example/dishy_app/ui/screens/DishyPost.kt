package com.example.dishy_app.ui.screens

data class VibeSpecs(
    val wifiSpeed: String = "Average",
    val comfortLevel: String = "Chairs",
    val noiseLevel: String = "Silent"
)

data class DishyPost(
    val id: String,
    val authorName: String,
    val authorPhotoUrl: String,
    val authorRole: String, // "USER" o "BUSINESS"
    val imageUrl: String,
    val description: String,
    val placeName: String,
    val location: String,
    val rating: Double = 0.0,
    val vibeSpecs: VibeSpecs = VibeSpecs(),
    val tags: List<String> = emptyList(),
    val timestamp: Long = System.currentTimeMillis()
)

val samplePosts = listOf(
    DishyPost(
        id = "1",
        authorName = "Elena Vibe-Seeker",
        authorPhotoUrl = "https://i.pravatar.cc/150?u=elena",
        authorRole = "USER",
        imageUrl = "https://images.unsplash.com/photo-1554118811-1e0d58224f24?w=800",
        description = "Finally found a spot that actually delivers on both the aesthetics and the work-from-anywhere essentials. The high-speed fiber here is no joke.",
        placeName = "The Velvet Roastery",
        location = "South District, Metropolis",
        rating = 4.8,
        vibeSpecs = VibeSpecs("High Speed", "Lounge", "Silent"),
        tags = listOf("UrbanWork", "VelvetRoastery", "MetropolisVibes")
    ),
    DishyPost(
        id = "2",
        authorName = "La Terraza Gourmet",
        authorPhotoUrl = "https://images.unsplash.com/photo-1517248135467-4c7edcad34c4",
        authorRole = "BUSINESS",
        imageUrl = "https://images.unsplash.com/photo-1481833761820-0509d3217039?w=800",
        description = "New seasonal menu is out! Come enjoy our sunset views and high-speed connection for your afternoon meetings.",
        placeName = "La Terraza Gourmet",
        location = "Madrid, Spain",
        rating = 4.9,
        vibeSpecs = VibeSpecs("High Speed", "Lounge", "Chatty"),
        tags = listOf("NewMenu", "SunsetWork", "GourmetVibe")
    ),
    DishyPost(
        id = "3",
        authorName = "Marco Polo",
        authorPhotoUrl = "https://i.pravatar.cc/150?u=marco",
        authorRole = "USER",
        imageUrl = "https://images.unsplash.com/photo-1501339817302-ee4f89ac3f1a?w=800",
        description = "Un rinconcito de paz en medio de la ciudad. Perfecto para leer o programar sin distracciones.",
        placeName = "Quiet Corner Cafe",
        location = "Armenia, Colombia",
        rating = 4.5,
        vibeSpecs = VibeSpecs("Average", "Chairs", "Silent"),
        tags = listOf("FocusMode", "QuietCafe", "Armenia")
    )
)
