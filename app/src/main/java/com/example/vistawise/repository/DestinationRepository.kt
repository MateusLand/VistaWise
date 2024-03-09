package com.example.vistawise.repository

import com.example.vistawise.model.Destination
import com.example.vistawise.network.DestinationService

class DestinationRepository(private val destinationService: DestinationService) {

    suspend fun getTopDestinations(): Result<List<Destination>> {
        return destinationService.retrieveTopDestinations()
    }
}

// is this everything for this repository so far?