package com.enzoroiz.tmdb.data.repository.artist.datasourceimplementation

import com.enzoroiz.tmdb.data.api.TMDBService
import com.enzoroiz.tmdb.data.model.artist.ArtistList
import com.enzoroiz.tmdb.data.repository.artist.datasource.ArtistRemoteDataSource
import retrofit2.Response

class ArtistRemoteDataSourceImpl(
    private val service: TMDBService,
    private val apiKey: String
): ArtistRemoteDataSource {
    override suspend fun getArtists(): Response<ArtistList> = service.getPopularArtists(apiKey)
}