package com.enzoroiz.tmdb.data.repository.tvshow.datasource

import com.enzoroiz.tmdb.data.model.tvshow.TVShowList
import retrofit2.Response

interface TVShowRemoteDataSource {
    suspend fun getTVShows(): Response<TVShowList>
}