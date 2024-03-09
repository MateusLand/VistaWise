package com.example.vistawise.network

import com.example.vistawise.model.User
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class UserService(private val auth: FirebaseAuth) {


    suspend fun login(email: String, password: String): Result<Boolean> {

        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun isUserLogged(): Result<Boolean> {
        val currentUser = auth.currentUser
        return if (currentUser != null) {
            Result.success(true)
        } else {
            Result.failure(IllegalAccessException("User not logged in"))
        }
    }

    suspend fun resetPassword(email: String): Result<Boolean> {
        return try {
            auth.sendPasswordResetEmail(email).await()
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun register(email: String, password: String): Result<Boolean> {
        return try {
            auth.createUserWithEmailAndPassword(email, password).await()
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getCurrentUser(): Result<User> {
        val currentUser = auth.currentUser
        return if (currentUser != null) {
            val user = User(currentUser.uid, currentUser.email, currentUser.displayName, null)
            Result.success(user)
        } else {
            Result.failure(IllegalAccessException("User not logged in"))
        }
    }

    suspend fun logout(): Result<Boolean> {
        return try {
            auth.signOut()
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}