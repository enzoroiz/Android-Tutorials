package com.enzoroiz.tmdb.data.repository.movie.datasource

import com.enzoroiz.tmdb.data.model.movie.MovieList
import retrofit2.Response

interface MovieRemoteDataSource {
    suspend fun getMovies(): Response<MovieList>
}