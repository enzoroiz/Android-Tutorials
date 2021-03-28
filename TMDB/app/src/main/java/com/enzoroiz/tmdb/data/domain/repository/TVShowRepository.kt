package com.enzoroiz.tmdb.data.domain.repository

import com.enzoroiz.tmdb.data.model.tvshow.TVShow

interface TVShowRepository {
    suspend fun getTVShows(): List<TVShow>
    suspend fun updateTVShows(): List<TVShow>
}