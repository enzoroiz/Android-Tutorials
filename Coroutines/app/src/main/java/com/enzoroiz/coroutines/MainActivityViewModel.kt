package com.enzoroiz.coroutines

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers

class MainActivityViewModel : ViewModel() {
    private val userRepository = UserRepository()
    val usersLiveData = liveData(Dispatchers.IO) {
        emit(userRepository.getUsers())
    }
}