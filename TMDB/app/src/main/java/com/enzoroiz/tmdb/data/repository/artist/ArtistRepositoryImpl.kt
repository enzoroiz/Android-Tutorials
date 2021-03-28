package com.enzoroiz.tmdb.data.repository.artist

import android.util.Log
import com.enzoroiz.tmdb.data.domain.repository.ArtistsRepository
import com.enzoroiz.tmdb.data.model.artist.Artist
import com.enzoroiz.tmdb.data.repository.artist.datasource.ArtistCacheDataSource
import com.enzoroiz.tmdb.data.repository.artist.datasource.ArtistLocalDataSource
import com.enzoroiz.tmdb.data.repository.artist.datasource.ArtistRemoteDataSource
import java.lang.Exception

class ArtistRepositoryImpl(
    private val cacheDataSource: ArtistCacheDataSource,
    private val localDataSource: ArtistLocalDataSource,
    private val remoteDataSource: ArtistRemoteDataSource
): ArtistsRepository {
    override suspend fun getArtists(): List<Artist> = getArtistsFromCache()

    override suspend fun updateArtists(): List<Artist> {
        val artists = getArtistsFromAPI()
        localDataSource.deleteAllArtists()
        localDataSource.saveArtists(artists)
        cacheDataSource.saveArtists(artists)

        return artists
    }

    private suspend fun getArtistsFromCache(): List<Artist> {
        var artists = listOf<Artist>()
        try {
            artists = cacheDataSource.getArtists()
        } catch (e: Exception) {
            Log.e(this.javaClass.simpleName, "Error while retrieving artists from cache database")
        }

        if (artists.isEmpty()) {
            artists = getArtistsFromDatabase()
            cacheDataSource.saveArtists(artists)
        }

        return artists
    }

    private suspend fun getArtistsFromDatabase(): List<Artist> {
        var artists = listOf<Artist>()
        try {
            artists = localDataSource.getArtists()
        } catch (e: Exception) {
            Log.e(this.javaClass.simpleName, "Error while retrieving artists from local database")
        }

        if (artists.isEmpty()) {
            artists = getArtistsFromAPI()
            localDataSource.saveArtists(artists)
        }

        return artists
    }

    private suspend fun getArtistsFromAPI(): List<Artist> {
        var artists = listOf<Artist>()
        try {
            remoteDataSource.getArtists().body()?.let {
                artists = it.artists
            }
        } catch (e: Exception) {
            Log.e(this.javaClass.simpleName, "Error while fetching artists from API")
        }

        return artists
    }
}