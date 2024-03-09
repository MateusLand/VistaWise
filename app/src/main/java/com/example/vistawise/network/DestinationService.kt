package com.example.vistawise.network

import com.example.vistawise.R
import com.example.vistawise.model.Destination

class DestinationService {

    suspend fun retrieveTopDestinations(): Result<List<Destination>> {
        return try {
            val destinations = listOf(
                Destination("London", R.drawable.london),
                Destination("Paris", R.drawable.paris),
                Destination("Budapest", R.drawable.budapest),
                Destination("Rome", R.drawable.rome)
                // Add more destinations as needed
            )
            Result.success(destinations)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}


// add links URL to images