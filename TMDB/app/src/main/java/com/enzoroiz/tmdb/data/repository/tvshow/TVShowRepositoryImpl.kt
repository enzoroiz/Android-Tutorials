package com.enzoroiz.tmdb.data.repository.tvshow

import android.util.Log
import com.enzoroiz.tmdb.data.domain.repository.TVShowRepository
import com.enzoroiz.tmdb.data.model.tvshow.TVShow
import com.enzoroiz.tmdb.data.repository.tvshow.datasource.TVShowCacheDataSource
import com.enzoroiz.tmdb.data.repository.tvshow.datasource.TVShowLocalDataSource
import com.enzoroiz.tmdb.data.repository.tvshow.datasource.TVShowRemoteDataSource
import java.lang.Exception

class TVShowRepositoryImpl(
    private val cacheDataSource: TVShowCacheDataSource,
    private val localDataSource: TVShowLocalDataSource,
    private val remoteDataSource: TVShowRemoteDataSource
): TVShowRepository {
    override suspend fun getTVShows(): List<TVShow> = getTVShowsFromCache()

    override suspend fun updateTVShows(): List<TVShow> {
        val tvShows = getTVShowsFromAPI()
        localDataSource.deleteAllTVShows()
        localDataSource.saveTVShows(tvShows)
        cacheDataSource.saveTVShows(tvShows)

        return tvShows
    }

    private suspend fun getTVShowsFromCache(): List<TVShow> {
        var tvShows = listOf<TVShow>()
        try {
            tvShows = cacheDataSource.getTVShows()
        } catch (e: Exception) {
            Log.e(this.javaClass.simpleName, "Error while retrieving TV shows from cache database")
        }

        if (tvShows.isEmpty()) {
            tvShows = getTVShowsFromDatabase()
            cacheDataSource.saveTVShows(tvShows)
        }

        return tvShows
    }

    private suspend fun getTVShowsFromDatabase(): List<TVShow> {
        var tvShows = listOf<TVShow>()
        try {
            tvShows = localDataSource.getTVShows()
        } catch (e: Exception) {
            Log.e(this.javaClass.simpleName, "Error while retrieving TV shows from local database")
        }

        if (tvShows.isEmpty()) {
            tvShows = getTVShowsFromAPI()
            localDataSource.saveTVShows(tvShows)
        }

        return tvShows
    }

    private suspend fun getTVShowsFromAPI(): List<TVShow> {
        var tvShows = listOf<TVShow>()
        try {
            remoteDataSource.getTVShows().body()?.let {
                tvShows = it.tvShows
            }
        } catch (e: Exception) {
            Log.e(this.javaClass.simpleName, "Error while fetching movies from API")
        }

        return tvShows
    }
}