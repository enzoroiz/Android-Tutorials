package com.enzoroiz.roomdatabase.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Subscriber.TABLE_NAME)
data class Subscriber(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    val id: Long,

    @ColumnInfo(name = COLUMN_NAME)
    var name: String,

    @ColumnInfo(name = COLUMN_EMAIL)
    var email: String
) {
    companion object {
        const val TABLE_NAME = "subscriber"
        const val COLUMN_ID = "subscriber_id"
        const val COLUMN_NAME = "subscriber_name"
        const val COLUMN_EMAIL = "subscriber_email"
    }
}