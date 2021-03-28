package com.enzoroiz.tmdb.data.domain.usecase

import com.enzoroiz.tmdb.data.domain.repository.TVShowRepository

class UpdateTVShowsUseCase(private val repository: TVShowRepository) {
    suspend fun execute() = repository.updateTVShows()
}