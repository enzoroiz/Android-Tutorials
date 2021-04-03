package com.enzoroiz.tmdb.presentation.di.core

import com.enzoroiz.tmdb.data.repository.artist.datasource.ArtistCacheDataSource
import com.enzoroiz.tmdb.data.repository.artist.datasourceimplementation.ArtistCacheDataSourceImpl
import com.enzoroiz.tmdb.data.repository.movie.datasource.MovieCacheDataSource
import com.enzoroiz.tmdb.data.repository.movie.datasourceimplementation.MovieCacheDataSourceImpl
import com.enzoroiz.tmdb.data.repository.tvshow.datasource.TVShowCacheDataSource
import com.enzoroiz.tmdb.data.repository.tvshow.datasourceimplementation.TVShowCacheDataSourceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CacheDataSourceModule {

    @Singleton
    @Provides
    fun provideMovieCacheDataSource() : MovieCacheDataSource {
        return MovieCacheDataSourceImpl()
    }

    @Singleton
    @Provides
    fun provideTVShowCacheDataSource() : TVShowCacheDataSource {
        return TVShowCacheDataSourceImpl()
    }

    @Singleton
    @Provides
    fun provideArtistCacheDataSource() : ArtistCacheDataSource {
        return ArtistCacheDataSourceImpl()
    }
}