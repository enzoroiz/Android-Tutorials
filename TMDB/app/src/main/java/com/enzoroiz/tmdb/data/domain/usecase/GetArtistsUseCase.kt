package com.enzoroiz.tmdb.data.domain.usecase

import com.enzoroiz.tmdb.data.domain.repository.ArtistsRepository

class GetArtistsUseCase(private val repository: ArtistsRepository) {
    suspend fun execute() = repository.getArtists()
}