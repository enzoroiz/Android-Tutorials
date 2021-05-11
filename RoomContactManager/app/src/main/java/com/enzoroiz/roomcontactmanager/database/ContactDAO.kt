package com.enzoroiz.roomcontactmanager.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveContact(contact: Contact): Long

    @Update
    suspend fun updateContact(contact: Contact): Int

    @Delete
    suspend fun deleteContact(contact: Contact): Int

    @Query("SELECT * FROM contacts")
    fun getAllContacts(): Flow<List<Contact>>

    @Query("SELECT * FROM contacts WHERE contact_id == :id")
    fun getContactById(id: Long): Flow<Contact>
}