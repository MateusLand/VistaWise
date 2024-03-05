package com.example.vistawise.module.di

import com.example.vistawise.viewmodel.LoginViewModel
import com.example.vistawise.viewmodel.RegisterViewModel
import com.example.vistawise.viewmodel.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        LoginViewModel(
            userRepository = get()
        )
    }

    viewModel {
        RegisterViewModel(
            userRepository = get()
        )
    }

    viewModel {
        SplashViewModel(
            userRepository = get()
        )
    }
}