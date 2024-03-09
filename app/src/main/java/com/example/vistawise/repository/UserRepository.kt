package com.example.vistawise.repository

import com.example.vistawise.model.User
import com.example.vistawise.network.UserService

class UserRepository(private val userService: UserService) {

    suspend fun loginUser(email: String, password: String): Result<Boolean> {
        return userService.login(email, password)
    }

    suspend fun isUserLogged(): Result<Boolean> {
        return userService.isUserLogged()
    }

    suspend fun resetPassword(email: String): Result<Boolean> {
        return userService.resetPassword(email)
    }

    suspend fun registerUser(email: String, password: String): Result<Boolean> {
        return userService.register(email, password)
    }

    suspend fun getUser(): Result<User> {
        return userService.getCurrentUser()
    }

    suspend fun logout(): Result<Boolean> {
        return userService.logout()
    }

}


