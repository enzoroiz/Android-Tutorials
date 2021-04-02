package com.enzoroiz.tmdb.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.enzoroiz.tmdb.data.database.dao.ArtistDAO
import com.enzoroiz.tmdb.data.database.dao.MovieDAO
import com.enzoroiz.tmdb.data.database.dao.TVShowDAO
import com.enzoroiz.tmdb.data.model.artist.Artist
import com.enzoroiz.tmdb.data.model.movie.Movie
import com.enzoroiz.tmdb.data.model.tvshow.TVShow

private const val DATABASE_VERSION = 1
@Database(
    entities = [Movie::class, TVShow::class, Artist::class],
    version = DATABASE_VERSION,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getMovieDAO(): MovieDAO
    abstract fun getTVShowDAO(): TVShowDAO
    abstract fun getArtistDAO(): ArtistDAO
}