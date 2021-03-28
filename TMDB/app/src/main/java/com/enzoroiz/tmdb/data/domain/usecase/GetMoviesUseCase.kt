package com.enzoroiz.tmdb.data.domain.usecase

import com.enzoroiz.tmdb.data.domain.repository.MovieRepository

class GetMoviesUseCase(private val repository: MovieRepository) {
    suspend fun execute() = repository.getMovies()
}