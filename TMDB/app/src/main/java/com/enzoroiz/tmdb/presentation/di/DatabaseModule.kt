package com.enzoroiz.tmdb.presentation.di

import android.content.Context
import androidx.room.Room
import com.enzoroiz.tmdb.data.database.AppDatabase
import com.enzoroiz.tmdb.data.database.dao.ArtistDAO
import com.enzoroiz.tmdb.data.database.dao.MovieDAO
import com.enzoroiz.tmdb.data.database.dao.TVShowDAO
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

private const val DATABASE_NAME = "app_database"

@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase() {
        fun getInstance(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()
        }
    }

    @Singleton
    @Provides
    fun provideMovieDAO(appDatabase: AppDatabase): MovieDAO {
        return appDatabase.getMovieDAO()
    }

    @Singleton
    @Provides
    fun provideTVShowDAO(appDatabase: AppDatabase): TVShowDAO {
        return appDatabase.getTVShowDAO()
    }

    @Singleton
    @Provides
    fun provideArtistDAO(appDatabase: AppDatabase): ArtistDAO {
        return appDatabase.getArtistDAO()
    }
}