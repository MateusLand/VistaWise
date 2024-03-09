package com.example.vistawise.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vistawise.model.Destination
import com.example.vistawise.repository.DestinationRepository
import com.example.vistawise.repository.UserRepository
import kotlinx.coroutines.launch

sealed class MainResult {
    object Loading : MainResult()
    data class UserSuccess(val userName: String) : MainResult()
    data class UserError(val userError: Throwable) : MainResult()
    data class DestinationSuccess(val destinations: List<Destination>) : MainResult()
    data class DestinationError(val error: Throwable) : MainResult()
}

class MainViewModel(
    private val destinationRepository: DestinationRepository,
    private val userRepository: UserRepository
) :
    ViewModel() {

    private val _mainResult = MutableLiveData<MainResult>()
    val mainResult: LiveData<MainResult>
        get() = _mainResult

    fun getUser() {
        viewModelScope.launch {
            val result = userRepository.getUser()
            if (result.isSuccess) {
                _mainResult.value =
                    MainResult.UserSuccess((result.getOrNull() ?: "Hello User").toString())
            } else {
                _mainResult.value = MainResult.UserError(
                    result.exceptionOrNull() ?: Exception("Unknown error")
                )
            }
        }
    }

    fun getTopDestinations() {
        viewModelScope.launch {
            _mainResult.value = MainResult.Loading
            val result = destinationRepository.getTopDestinations()
            if (result.isSuccess) {
                _mainResult.value =
                    MainResult.DestinationSuccess(result.getOrNull() ?: emptyList())
            } else {
                _mainResult.value = MainResult.DestinationError(
                    result.exceptionOrNull() ?: Exception("Unknown error")
                )
            }
        }
    }
}
