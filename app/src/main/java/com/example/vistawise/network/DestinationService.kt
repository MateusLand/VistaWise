package com.example.vistawise.network

import com.example.vistawise.model.Destination

class DestinationService {

    suspend fun retrieveTopDestinations(): Result<List<Destination>> {
        return try {
            val mainDestinationUrls = listOf(
                Destination("London", "https://media.cntraveler.com/photos/6362cedd30f7bfad82fb4d8e/master/w_1600,c_limit/big%20ben-GettyImages-1388339818.jpeg"),
                Destination("Paris", "https://c0.wallpaperflare.com/preview/73/406/408/architecture-city-travel-sky.jpg"),
                Destination("Budapest", "https://i.pinimg.com/736x/7b/30/03/7b30037132197c4940452608c07e728b.jpg"),
                Destination("Rome", "https://a.storyblok.com/f/53624/1365x2040/cea2c909f6/young-womans-standing-in-front-of-the-colosseum.jpg")
                // Add more destinations as needed
            )
            Result.success(mainDestinationUrls)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
