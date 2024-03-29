package com.example.vistawise.repository

import com.example.vistawise.model.Destination
import com.example.vistawise.network.DestinationService

class DestinationRepository(private val destinationService: DestinationService) {

    suspend fun getTopDestinations(): Result<List<Destination>> {
        return destinationService.retrieveTopDestinations()
    }

    suspend fun getCountries() {
        return destinationService.getCountries()
    }
}
