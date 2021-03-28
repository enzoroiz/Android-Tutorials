package com.enzoroiz.tmdb.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.enzoroiz.tmdb.data.database.dao.ArtistDAO
import com.enzoroiz.tmdb.data.database.dao.MovieDAO
import com.enzoroiz.tmdb.data.database.dao.TVShowDAO
import com.enzoroiz.tmdb.data.model.tvshow.TVShow

private const val DATABASE_VERSION = 1
private const val DATABASE_NAME = "app_database"

@Database(
    entities = [Artist::class, TVShow::class, Artist::class],
    version = DATABASE_VERSION,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    private var instance: AppDatabase? = null

    abstract fun getMovieDAO(): MovieDAO
    abstract fun getTVShowDAO(): TVShowDAO
    abstract fun getArtistDAO(): ArtistDAO

    fun getInstance(context: Context): AppDatabase {
        synchronized(this) {
            instance ?: let {
                instance =
                    Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()
            }

            return instance!!
        }
    }
}