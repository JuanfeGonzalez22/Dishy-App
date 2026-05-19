package com.example.dishy_app.data.repository

import com.example.dishy_app.data.model.Place
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class PlaceRepository {
    private val db = FirebaseFirestore.getInstance()
    private val placesCollection = db.collection("places")

    // Obtiene todos los lugares desde Firestore
    suspend fun getAllPlaces(): List<Place> {
        return try {
            val snapshot = placesCollection.get().await()
            snapshot.toObjects(Place::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    // Obtiene un lugar por ID (ahora el ID será el DocumentID de Firebase)
    suspend fun getPlaceById(id: String): Place? {
        return try {
            val document = placesCollection.document(id).get().await()
            document.toObject(Place::class.java)
        } catch (e: Exception) {
            null
        }
    }
}
