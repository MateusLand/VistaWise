package com.example.vistawise.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vistawise.repository.UserRepository
import kotlinx.coroutines.launch

class SplashViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _loginStatus = MutableLiveData<Boolean>()
    val loginStatus: LiveData<Boolean> = _loginStatus

    fun isUserLogged() {
        viewModelScope.launch {
            val result = userRepository.isUserLogged()
            result.onSuccess {
                _loginStatus.value = true
            }.onFailure {
                _loginStatus.value = false
            }
        }
    }
}