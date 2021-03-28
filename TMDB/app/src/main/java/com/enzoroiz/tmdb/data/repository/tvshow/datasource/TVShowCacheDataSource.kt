package com.enzoroiz.tmdb.data.repository.tvshow.datasource

import com.enzoroiz.tmdb.data.model.tvshow.TVShow

interface TVShowCacheDataSource {
    fun getTVShows(): List<TVShow>
    fun saveTVShows(tvShows: List<TVShow>)
}