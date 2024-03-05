package com.example.vistawise.module.di

import com.example.vistawise.network.UserService
import com.google.firebase.auth.FirebaseAuth
import org.koin.dsl.module

val networkModule = module {

    single {
        FirebaseAuth.getInstance()
    }

    single {
        UserService(get())
    }

    // Add other network-related dependencies like Retrofit, OkHttpClient, API services, etc.
}
