package com.enzoroiz.roomdatabase

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.enzoroiz.roomdatabase.database.SubscriberRepository
import java.lang.IllegalArgumentException

class MainActivityViewModelFactory(
    private val application: Application,
    private val repository: SubscriberRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        modelClass.cast(MainActivityViewModel(application, repository))
            ?: throw IllegalArgumentException("Could not cast ${modelClass.name} into MainActivityViewModel")
}