package com.example.vistawise.viewmodel

import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {
}

//    private val auth = FirebaseAuth.getInstance()
//    private val _currentUser = MutableLiveData<FirebaseUser>()
//    val currentUser: LiveData<FirebaseUser> = _currentUser
//    private val _errorMessage = MutableLiveData<String>()
//    val errorMessage: LiveData<String> = _errorMessage
//
//    init {
//        _currentUser.value = auth.currentUser
//    }
//
//    fun logout() {
//        auth.signOut()
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    _currentUser.value = null
//                } else {
//                    _errorMessage.value = task.exception?.message ?: "Unknown error occurred"
//                }
//            }
//    }
//}

