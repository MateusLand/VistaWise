package com.example.vistawise.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vistawise.repository.UserRepository
import kotlinx.coroutines.launch


// UserAuthResult is a sealed class that can be either Loading, UserAuthSuccess or UserAuthError
sealed class UserAuthResult {
    object Loading : UserAuthResult()
    object UserAuthSuccess : UserAuthResult()  // Replace User with your user data class
    data class UserAuthError(val throwable: Throwable) : UserAuthResult()
    data class PasswordResetError(val throwable: Throwable) : UserAuthResult()
    object PasswordResetSuccess : UserAuthResult()
}

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {

    // Mutable live data for user auth status
    private val _userAuthStatus = MutableLiveData<UserAuthResult>()
    val userAuthStatus: LiveData<UserAuthResult> = _userAuthStatus

    // Function to handle user login that interacts with the repository
    fun loginUser(email: String, password: String) {
        _userAuthStatus.value = UserAuthResult.Loading
        viewModelScope.launch {
            val result = userRepository.loginUser(email, password)
            result.onSuccess {
                _userAuthStatus.value = UserAuthResult.UserAuthSuccess
            }.onFailure {
                _userAuthStatus.value = UserAuthResult.UserAuthError(it)
            }
        }
    }

    // Function to handle password reset that interacts with repository
    fun resetPassword(email: String) {
        _userAuthStatus.value = UserAuthResult.Loading
        viewModelScope.launch {
            val result = userRepository.resetPassword(email)
            result.onSuccess {
                _userAuthStatus.value = UserAuthResult.PasswordResetSuccess
            }.onFailure {
                _userAuthStatus.value = UserAuthResult.PasswordResetError(it)
            }
        }
    }
}