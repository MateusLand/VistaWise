package com.example.vistawise.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vistawise.repository.UserRepository
import kotlinx.coroutines.launch

// UserRegistrationResult is a sealed class that represents the result of a user registration
sealed class UserRegistrationResult {
    object Loading : UserRegistrationResult()
    object UserRegistrationSuccess : UserRegistrationResult()
    object UserAlreadyExists : UserRegistrationResult()
    data class UserRegistrationError(val error: Throwable) : UserRegistrationResult()
}

class RegisterViewModel(private val userRepository: UserRepository) : ViewModel() {

    // LiveData for user registration status
    private val _userRegistrationStatus = MutableLiveData<UserRegistrationResult>()
    val userRegistrationStatus: LiveData<UserRegistrationResult> = _userRegistrationStatus

    // Function to register a new user that interacts with the repository
    fun registerUser(email: String, password: String, name: String) {
        _userRegistrationStatus.value = UserRegistrationResult.Loading
        viewModelScope.launch {
            val result = userRepository.registerUser(email, password)
            result.onSuccess {
                _userRegistrationStatus.value = UserRegistrationResult.UserRegistrationSuccess
            }.onFailure {
                _userRegistrationStatus.value = UserRegistrationResult.UserRegistrationError(it)
            }
        }
    }
}