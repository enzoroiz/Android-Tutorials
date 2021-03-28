package com.enzoroiz.tmdb.data.repository.artist.datasource

import com.enzoroiz.tmdb.data.model.artist.Artist

interface ArtistCacheDataSource {
    fun getArtists(): List<Artist>
    fun saveArtists(artists: List<Artist>)
}