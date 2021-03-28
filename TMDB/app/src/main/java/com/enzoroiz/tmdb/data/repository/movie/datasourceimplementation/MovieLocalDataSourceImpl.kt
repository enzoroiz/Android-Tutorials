package com.enzoroiz.tmdb.data.repository.movie.datasourceimplementation

import com.enzoroiz.tmdb.data.database.dao.MovieDAO
import com.enzoroiz.tmdb.data.model.movie.Movie
import com.enzoroiz.tmdb.data.repository.movie.datasource.MovieLocalDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieLocalDataSourceImpl(private val movieDAO: MovieDAO):
    MovieLocalDataSource {
    override suspend fun getMovies(): List<Movie> = movieDAO.getMovies()

    override suspend fun saveMovies(movies: List<Movie>) {
        CoroutineScope(Dispatchers.IO).launch {
            movieDAO.saveMovies(movies)
        }
    }

    override suspend fun deleteAllMovies() {
        CoroutineScope(Dispatchers.IO).launch {
            movieDAO.deleteAllMovies()
        }
    }
}