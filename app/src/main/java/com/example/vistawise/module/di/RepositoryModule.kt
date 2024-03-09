package com.example.vistawise.module.di

import com.example.vistawise.network.DestinationService
import com.example.vistawise.repository.DestinationRepository
import com.example.vistawise.repository.UserRepository
import org.koin.dsl.module

val repositoryModule = module {

    single {
        UserRepository(get())
    }

    single {
        DestinationRepository(get())
    }

    single {
        DestinationService()
    }
}