package com.enzoroiz.tmdb.data.repository.movie.datasourceimplementation

import com.enzoroiz.tmdb.data.model.movie.Movie
import com.enzoroiz.tmdb.data.repository.movie.datasource.MovieCacheDataSource

class MovieCacheDataSourceImpl:
    MovieCacheDataSource {
    private var cachedMovies = mutableListOf<Movie>()

    override fun getMovies() = cachedMovies

    override fun saveMovies(movies: List<Movie>) {
        cachedMovies.clear()
        cachedMovies = ArrayList(movies)
    }
}