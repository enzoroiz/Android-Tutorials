package com.enzoroiz.tmdb.data.repository.movie

import android.util.Log
import com.enzoroiz.tmdb.data.domain.repository.MovieRepository
import com.enzoroiz.tmdb.data.model.movie.Movie
import com.enzoroiz.tmdb.data.repository.movie.datasource.MovieCacheDataSource
import com.enzoroiz.tmdb.data.repository.movie.datasource.MovieLocalDataSource
import com.enzoroiz.tmdb.data.repository.movie.datasource.MovieRemoteDataSource
import java.lang.Exception

class MovieRepositoryImpl(
    private val cacheDataSource: MovieCacheDataSource,
    private val localDataSource: MovieLocalDataSource,
    private val remoteDataSource: MovieRemoteDataSource
): MovieRepository {
    override suspend fun getMovies(): List<Movie> = getMoviesFromCache()

    override suspend fun updateMovies(): List<Movie> {
        val movies = getMoviesFromAPI()
        localDataSource.deleteAllMovies()
        localDataSource.saveMovies(movies)
        cacheDataSource.saveMovies(movies)

        return movies
    }

    private suspend fun getMoviesFromCache(): List<Movie> {
        var movies = listOf<Movie>()
        try {
            movies = cacheDataSource.getMovies()
        } catch (e: Exception) {
            Log.e(this.javaClass.simpleName, "Error while retrieving movies from cache database")
        }

        if (movies.isEmpty()) {
            movies = getMoviesFromDatabase()
            cacheDataSource.saveMovies(movies)
        }

        return movies
    }

    private suspend fun getMoviesFromDatabase(): List<Movie> {
        var movies = listOf<Movie>()
        try {
            movies = localDataSource.getMovies()
        } catch (e: Exception) {
            Log.e(this.javaClass.simpleName, "Error while retrieving movies from local database")
        }

        if (movies.isEmpty()) {
            movies = getMoviesFromAPI()
            localDataSource.saveMovies(movies)
        }

        return movies
    }

    private suspend fun getMoviesFromAPI(): List<Movie> {
        var movies = listOf<Movie>()
        try {
            remoteDataSource.getMovies().body()?.let {
                movies = it.movies
            }
        } catch (e: Exception) {
            Log.e(this.javaClass.simpleName, "Error while fetching movies from API")
        }

        return movies
    }
}