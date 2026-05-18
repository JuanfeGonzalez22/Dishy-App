package com.example.dishy_app.data.model

data class Place(
    val id: Int = 0,
    val name: String = "",
    val category: String = "",
    val rating: Double = 0.0,
    val reviews: Int = 0,
    val distance: String = "",
    val address: String = "",
    val imageUrl: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val description: String = ""
)
