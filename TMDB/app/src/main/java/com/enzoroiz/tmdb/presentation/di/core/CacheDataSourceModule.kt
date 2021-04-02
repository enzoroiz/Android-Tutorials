package com.enzoroiz.tmdb.presentation.di.core

import com.enzoroiz.tmdb.data.repository.movie.datasource.MovieCacheDataSource
import com.enzoroiz.tmdb.data.repository.movie.datasourceimplementation.MovieCacheDataSourceImpl
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
    fun provideTVShowCacheDataSource() : MovieCacheDataSource {
        return MovieCacheDataSourceImpl()
    }

    @Singleton
    @Provides
    fun provideArtistCacheDataSource() : MovieCacheDataSource {
        return MovieCacheDataSourceImpl()
    }
}