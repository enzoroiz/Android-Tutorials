package com.enzoroiz.tmdb.data.repository.tvshow.datasource

import com.enzoroiz.tmdb.data.model.tvshow.TVShow

interface TVShowLocalDataSource {
    suspend fun getTVShows(): List<TVShow>
    suspend fun saveTVShows(tvShows: List<TVShow>)
    suspend fun deleteAllTVShows()
}