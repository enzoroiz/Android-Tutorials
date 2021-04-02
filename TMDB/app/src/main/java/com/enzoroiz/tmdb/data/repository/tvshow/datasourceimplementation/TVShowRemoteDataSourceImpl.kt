package com.enzoroiz.tmdb.data.repository.tvshow.datasourceimplementation

import com.enzoroiz.tmdb.data.api.TMDBService
import com.enzoroiz.tmdb.data.model.tvshow.TVShowList
import com.enzoroiz.tmdb.data.repository.tvshow.datasource.TVShowRemoteDataSource
import retrofit2.Response

class TVShowRemoteDataSourceImpl(
    private val service: TMDBService,
    private val apiKey: String
): TVShowRemoteDataSource {
    override suspend fun getTVShows(): Response<TVShowList> = service.getPopularTVShows(apiKey)
}