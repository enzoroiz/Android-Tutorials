package com.enzoroiz.tmdb.data.repository.tvshow.datasourceimplementation

import com.enzoroiz.tmdb.data.database.dao.TVShowDAO
import com.enzoroiz.tmdb.data.model.tvshow.TVShow
import com.enzoroiz.tmdb.data.repository.tvshow.datasource.TVShowLocalDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TVShowLocalDataSourceImpl(private val tvShowDAO: TVShowDAO):
    TVShowLocalDataSource {
    override suspend fun getTVShows(): List<TVShow> = tvShowDAO.getTVShows()

    override suspend fun saveTVShows(tvShows: List<TVShow>) {
        CoroutineScope(Dispatchers.IO).launch {
            tvShowDAO.saveTVShows(tvShows)
        }
    }

    override suspend fun deleteAllTVShows() {
        CoroutineScope(Dispatchers.IO).launch {
            tvShowDAO.deleteAllTVShows()
        }
    }
}