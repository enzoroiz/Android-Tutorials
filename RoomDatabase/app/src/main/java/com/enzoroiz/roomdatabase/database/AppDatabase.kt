package com.enzoroiz.roomdatabase.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Subscriber::class], version = AppDatabase.DB_VERSION)
abstract class AppDatabase: RoomDatabase() {

    abstract val subscriberDAO: SubscriberDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        const val DB_VERSION = 1
        private const val DB_NAME = "app_database"

        fun getInstance(context: Context) : AppDatabase {
            synchronized(this) {
                return INSTANCE ?: let {
                    Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        DB_NAME
                    ).build()
                }
            }
        }
    }
}