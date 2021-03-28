package com.enzoroiz.tmdb.data.repository.artist.datasource

import com.enzoroiz.tmdb.data.model.artist.Artist

interface ArtistLocalDataSource {
    suspend fun getArtists(): List<Artist>
    suspend fun saveArtists(artists: List<Artist>)
    suspend fun deleteAllArtists()
}