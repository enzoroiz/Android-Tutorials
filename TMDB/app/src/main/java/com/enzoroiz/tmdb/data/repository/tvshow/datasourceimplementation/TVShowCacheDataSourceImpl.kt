package com.enzoroiz.tmdb.data.repository.tvshow.datasourceimplementation

import com.enzoroiz.tmdb.data.model.tvshow.TVShow
import com.enzoroiz.tmdb.data.repository.tvshow.datasource.TVShowCacheDataSource

class TVShowCacheDataSourceImpl:
    TVShowCacheDataSource {
    private var cachedTVShows = mutableListOf<TVShow>()

    override fun getTVShows() = cachedTVShows

    override fun saveTVShows(tvShows: List<TVShow>) {
        cachedTVShows.clear()
        cachedTVShows = ArrayList(tvShows)
    }
}