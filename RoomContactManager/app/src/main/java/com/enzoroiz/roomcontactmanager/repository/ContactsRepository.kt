package com.enzoroiz.roomcontactmanager.repository

import com.enzoroiz.roomcontactmanager.database.Contact
import javax.inject.Inject

class ContactsRepository @Inject constructor(private val localDataSource: LocalDataSource) {
    suspend fun saveContactToDB(contact: Contact) = localDataSource.saveContact(contact)
    suspend fun updateContactInDB(contact: Contact) = localDataSource.updateContact(contact)
    suspend fun deleteContactFromDB(contact: Contact) = localDataSource.deleteContact(contact)
    fun getContactsFromDB() = localDataSource.getAllContacts()
    fun getContactFromByIdFromDB(id: Long) = localDataSource.getContactById(id)
}