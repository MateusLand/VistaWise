package com.example.vistawise

import android.app.Application
import android.util.Log
import com.example.vistawise.module.di.networkModule
import com.example.vistawise.module.di.repositoryModule
import com.example.vistawise.module.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin


class VistaWiseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Log.d("VistaWiseApplication", "Mateus")

        startKoin {
            androidLogger()
            androidContext(this@VistaWiseApplication)
            modules(networkModule)
            modules(repositoryModule)
            modules(viewModelModule)
        }
    }
}