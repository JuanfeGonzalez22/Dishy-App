package com.example.dishy_app.data.repository

import com.example.dishy_app.data.model.Place
import com.google.firebase.firestore.FirebaseFirestore

class PlaceRepository {
    // Lista de prueba con los campos corregidos (reviews y distance)
    private val samplePlaces = listOf(
        Place(
            id = 1,
            name = "La Parrilla de Erick",
            category = "Carnes",
            rating = 4.8,
            reviews = 124,
            distance = "0.5 km",
            address = "Av. Principal 123",
            imageUrl = "https://images.unsplash.com/photo-1544025162-d76694265947",
            description = "El mejor asado de la ciudad con cortes premium."
        ),
        Place(
            id = 2,
            name = "Sushi Master",
            category = "Japonesa",
            rating = 4.5,
            reviews = 89,
            distance = "1.2 km",
            address = "Calle 45 #10-20",
            imageUrl = "https://images.unsplash.com/photo-1579871494447-9811cf80d66c",
            description = "Sushi fresco y rollos creativos todos los días."
        ),
        Place(
            id = 3,
            name = "Café del Bosque",
            category = "Cafetería",
            rating = 4.9,
            reviews = 250,
            distance = "2.0 km",
            address = "Carrera 7 #45-90",
            imageUrl = "https://images.unsplash.com/photo-1501339847302-ac426a4a7cbb",
            description = "Ambiente tranquilo ideal para trabajar."
        )
    )

    fun getAllPlaces(): List<Place> = samplePlaces

    fun getPlaceById(id: Int): Place? = samplePlaces.find { it.id == id }
}
