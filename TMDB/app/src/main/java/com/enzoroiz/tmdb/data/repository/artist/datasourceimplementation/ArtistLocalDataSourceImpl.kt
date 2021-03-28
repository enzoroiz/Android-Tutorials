package com.enzoroiz.tmdb.data.repository.artist.datasourceimplementation

import com.enzoroiz.tmdb.data.database.dao.ArtistDAO
import com.enzoroiz.tmdb.data.model.artist.Artist
import com.enzoroiz.tmdb.data.repository.artist.datasource.ArtistLocalDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ArtistLocalDataSourceImpl(private val artistDAO: ArtistDAO):
    ArtistLocalDataSource {

    override suspend fun getArtists(): List<Artist> = artistDAO.getArtists()

    override suspend fun saveArtists(artists: List<Artist>) {
        CoroutineScope(Dispatchers.IO).launch {
            artistDAO.saveArtists(artists)
        }
    }

    override suspend fun deleteAllArtists() {
        CoroutineScope(Dispatchers.IO).launch {
            artistDAO.deleteAllArtists()
        }
    }
}