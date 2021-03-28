package com.enzoroiz.tmdb.data.domain.repository

import com.enzoroiz.tmdb.data.model.movie.Movie

interface MovieRepository {
    suspend fun getMovies(): List<Movie>
    suspend fun updateMovies(): List<Movie>
}