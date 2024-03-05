package com.example.vistawise.module.di

import com.example.vistawise.repository.UserRepository
import org.koin.dsl.module

val repositoryModule = module {

    single {
        UserRepository(get())
    }
}