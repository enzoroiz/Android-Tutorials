package com.enzoroiz.tmdb.data.domain.usecase

import com.enzoroiz.tmdb.data.domain.repository.TVShowRepository

class GetTVShowsUseCase(private val repository: TVShowRepository) {
    suspend fun execute() = repository.getTVShows()
}