package com.example.vistawise.network

import android.content.ContentValues.TAG
import android.util.Log
import com.example.vistawise.model.Destination
import com.google.firebase.firestore.FirebaseFirestore

class DestinationService {

    val db = FirebaseFirestore.getInstance()
    val citiesRef = db.collection("cities")

    suspend fun retrieveTopDestinations(): Result<List<Destination>> {
        getCountries()
        return try {
            val mainDestinationUrls = listOf(
                Destination(
                    "London",
                    "https://media.cntraveler.com/photos/6362cedd30f7bfad82fb4d8e/master/w_1600,c_limit/big%20ben-GettyImages-1388339818.jpeg"
                ),
                Destination(
                    "Paris",
                    "https://c0.wallpaperflare.com/preview/73/406/408/architecture-city-travel-sky.jpg"
                ),
                Destination(
                    "Budapest",
                    "https://i.pinimg.com/736x/7b/30/03/7b30037132197c4940452608c07e728b.jpg"
                ),
                Destination(
                    "Rome",
                    "https://a.storyblok.com/f/53624/1365x2040/cea2c909f6/young-womans-standing-in-front-of-the-colosseum.jpg"
                )
                // Add more destinations as needed
            )
            Result.success(mainDestinationUrls)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getCountries() {
        Log.d(TAG, "Fran")
        val query = citiesRef
            .limit(5)

        query.get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    // Handle each document
                    // TODO: map documents.data to City object
                    Log.d(TAG, "Felipe1: ${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                // Handle any errors
                Log.w(TAG, "Felipe2: Error getting documents: ", exception)
            }
    }
}

