package com.enzoroiz.tmdb.data.domain.repository

import com.enzoroiz.tmdb.data.model.artist.Artist

interface ArtistsRepository {
    suspend fun getArtists(): List<Artist>
    suspend fun updateArtists(): List<Artist>
}