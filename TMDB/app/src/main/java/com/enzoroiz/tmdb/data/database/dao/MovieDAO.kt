package com.enzoroiz.tmdb.data.database.dao

import androidx.room.*
import com.enzoroiz.tmdb.data.model.movie.Movie

@Dao
interface MovieDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovies(movies: List<Movie>)

    @Query("DELETE FROM movie")
    suspend fun deleteAllMovies()

    @Query("SELECT * FROM movie")
    suspend fun getMovies(): List<Movie>
}