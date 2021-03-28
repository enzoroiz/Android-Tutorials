package com.enzoroiz.tmdb.data.repository.artist.datasourceimplementation

import com.enzoroiz.tmdb.data.model.artist.Artist
import com.enzoroiz.tmdb.data.repository.artist.datasource.ArtistCacheDataSource

class ArtistCacheDataSourceImpl:
    ArtistCacheDataSource {
    private var cachedArtists = mutableListOf<Artist>()

    override fun getArtists(): List<Artist> = cachedArtists

    override fun saveArtists(artists: List<Artist>) {
        cachedArtists.clear()
        cachedArtists = ArrayList(artists)
    }
}