package com.enzoroiz.roomcontactmanager.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.enzoroiz.roomcontactmanager.repository.ContactsRepository
import java.lang.Exception

class ContactsViewModelFactory(private val repository: ContactsRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.cast(ContactsViewModel(repository))
            ?: throw Exception("Could not convert class to View Model!")
    }
}