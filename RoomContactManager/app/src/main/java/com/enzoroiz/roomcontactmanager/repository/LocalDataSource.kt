package com.enzoroiz.roomcontactmanager.repository

import com.enzoroiz.roomcontactmanager.database.AppDatabase
import com.enzoroiz.roomcontactmanager.database.Contact
import javax.inject.Inject

class LocalDataSource @Inject constructor(database: AppDatabase) {
    private val contactDAO = database.getContactDAO()

    suspend fun saveContact(contact: Contact) = contactDAO.saveContact(contact)
    suspend fun updateContact(contact: Contact) = contactDAO.updateContact(contact)
    suspend fun deleteContact(contact: Contact) = contactDAO.deleteContact(contact)
    fun getAllContacts() = contactDAO.getAllContacts()
    fun getContactById(id: Long) = contactDAO.getContactById(id)
}