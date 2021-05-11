package com.enzoroiz.roomcontactmanager.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
data class Contact(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "contact_id")
    var id: Long? = null,
    @ColumnInfo(name = "contact_name")
    var name: String,
    @ColumnInfo(name = "contact_email")
    var email: String
)