package com.enzoroiz.tmdb.data.repository.artist.datasource

import com.enzoroiz.tmdb.data.model.artist.ArtistList
import retrofit2.Response

interface ArtistRemoteDataSource {
    suspend fun getArtists(): Response<ArtistList>
}