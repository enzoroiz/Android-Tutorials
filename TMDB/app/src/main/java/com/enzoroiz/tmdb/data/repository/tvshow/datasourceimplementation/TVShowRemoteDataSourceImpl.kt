package com.enzoroiz.tmdb.data.repository.tvshow.datasourceimplementation

import com.enzoroiz.tmdb.data.api.TMDBService
import com.enzoroiz.tmdb.data.model.movie.MovieList
import com.enzoroiz.tmdb.data.repository.movie.datasource.MovieRemoteDataSource
import retrofit2.Response

class TVShowRemoteDataSourceImpl(
    private val service: TMDBService,
    private val apiKey: String
): MovieRemoteDataSource {
    override suspend fun getMovies(): Response<MovieList> = service.getPopularMovies(apiKey)
}