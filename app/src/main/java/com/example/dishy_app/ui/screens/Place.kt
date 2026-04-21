package com.example.dishy_app.ui.screens

data class Place(
    val id: Int,
    val name: String,
    val description: String,
    val distance: String,
    val rating: Double,
    val reviews: Int,
    val imageUrl: String,       
    val communityPhotos: List<String> 
)

val samplePlaces = listOf(
    Place(
        id = 1,
        name = "The Coffee Collective",
        description = "Cozy & Quiet",
        distance = "0.5 mi",
        rating = 4.8,
        reviews = 128,
        imageUrl = "https://images.unsplash.com/photo-1509042239860-f550ce710b93",
        communityPhotos = listOf("https://picsum.photos/200", "https://picsum.photos/201")
    ),
    Place(
        id = 2,
        name = "Nomad Workspace",
        description = "Productive",
        distance = "1.2 mi",
        rating = 4.9,
        reviews = 342,
        imageUrl = "https://images.unsplash.com/photo-1497366216548-37526070297c",
        communityPhotos = listOf("https://picsum.photos/202", "https://picsum.photos/203")
    ),
    Place(
        id = 3,
        name = "Bluebird Bistro",
        description = "Romantic",
        distance = "2.8 mi",
        rating = 4.6,
        reviews = 89,
        imageUrl = "https://images.unsplash.com/photo-1517248135467-4c7edcad34c4",
        communityPhotos = listOf("https://picsum.photos/204", "https://picsum.photos/205")
    )
)
