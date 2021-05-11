package com.enzoroiz.roomcontactmanager.viewmodel

import androidx.lifecycle.*
import com.enzoroiz.roomcontactmanager.database.Contact
import com.enzoroiz.roomcontactmanager.repository.ContactsRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ContactsViewModel constructor(private val repository: ContactsRepository): ViewModel() {
    private val contactByIdMutableLiveData = MutableLiveData<Contact>()
    val contactByIdLiveData: LiveData<Contact>
        get() = contactByIdMutableLiveData

    fun saveContact(contact: Contact) = viewModelScope.launch {
        repository.saveContactToDB(contact)
    }

    fun updateContact(contact: Contact) = viewModelScope.launch {
        repository.updateContactInDB(contact)
    }

    fun deleteContact(contact: Contact) = viewModelScope.launch {
        repository.deleteContactFromDB(contact)
    }

    fun getAllContacts() = repository.getContactsFromDB().asLiveData(viewModelScope.coroutineContext)

    fun getContactById(id: Long) {
        viewModelScope.launch {
            contactByIdMutableLiveData.postValue(repository.getContactFromByIdFromDB(id).first())
        }
    }
}
