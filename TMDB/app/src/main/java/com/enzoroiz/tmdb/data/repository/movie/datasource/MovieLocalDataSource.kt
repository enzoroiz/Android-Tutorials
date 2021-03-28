package com.enzoroiz.tmdb.data.repository.movie.datasource

import com.enzoroiz.tmdb.data.model.movie.Movie

interface MovieLocalDataSource {
    suspend fun getMovies(): List<Movie>
    suspend fun saveMovies(movies: List<Movie>)
    suspend fun deleteAllMovies()
}