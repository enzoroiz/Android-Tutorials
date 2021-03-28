package com.enzoroiz.tmdb.data.repository.movie.datasource

import com.enzoroiz.tmdb.data.model.movie.Movie

interface MovieCacheDataSource {
    fun getMovies(): List<Movie>
    fun saveMovies(movies: List<Movie>)
}