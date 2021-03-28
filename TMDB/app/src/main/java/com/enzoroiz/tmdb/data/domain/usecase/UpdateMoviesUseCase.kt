package com.enzoroiz.tmdb.data.domain.usecase

import com.enzoroiz.tmdb.data.domain.repository.MovieRepository

class UpdateMoviesUseCase(private val repository: MovieRepository) {
    suspend fun execute() = repository.updateMovies()
}