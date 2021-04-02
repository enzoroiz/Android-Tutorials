package com.enzoroiz.tmdb.presentation.di.core

import com.enzoroiz.tmdb.data.api.TMDBService
import com.enzoroiz.tmdb.data.repository.artist.datasource.ArtistRemoteDataSource
import com.enzoroiz.tmdb.data.repository.artist.datasourceimplementation.ArtistRemoteDataSourceImpl
import com.enzoroiz.tmdb.data.repository.movie.datasource.MovieRemoteDataSource
import com.enzoroiz.tmdb.data.repository.movie.datasourceimplementation.MovieRemoteDataSourceImpl
import com.enzoroiz.tmdb.data.repository.tvshow.datasource.TVShowRemoteDataSource
import com.enzoroiz.tmdb.data.repository.tvshow.datasourceimplementation.TVShowRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RemoteDataSourceModule(private val apiKey: String) {
    @Singleton
    @Provides
    fun provideMovieRemoteDataSource(tmdbService: TMDBService): MovieRemoteDataSource {
        return MovieRemoteDataSourceImpl(tmdbService, apiKey)
    }

    @Singleton
    @Provides
    fun provideTVShowRemoteDataSource(tmdbService: TMDBService): TVShowRemoteDataSource {
        return TVShowRemoteDataSourceImpl(tmdbService, apiKey)
    }

    @Singleton
    @Provides
    fun provideArtistRemoteDataSource(tmdbService: TMDBService): ArtistRemoteDataSource {
        return ArtistRemoteDataSourceImpl(tmdbService, apiKey)
    }
}