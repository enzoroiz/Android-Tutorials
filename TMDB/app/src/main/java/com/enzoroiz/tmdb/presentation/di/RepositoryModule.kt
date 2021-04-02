package com.enzoroiz.tmdb.presentation.di

import com.enzoroiz.tmdb.data.domain.repository.ArtistsRepository
import com.enzoroiz.tmdb.data.domain.repository.MovieRepository
import com.enzoroiz.tmdb.data.domain.repository.TVShowRepository
import com.enzoroiz.tmdb.data.repository.artist.ArtistRepositoryImpl
import com.enzoroiz.tmdb.data.repository.artist.datasource.ArtistCacheDataSource
import com.enzoroiz.tmdb.data.repository.artist.datasource.ArtistLocalDataSource
import com.enzoroiz.tmdb.data.repository.artist.datasource.ArtistRemoteDataSource
import com.enzoroiz.tmdb.data.repository.movie.MovieRepositoryImpl
import com.enzoroiz.tmdb.data.repository.movie.datasource.MovieCacheDataSource
import com.enzoroiz.tmdb.data.repository.movie.datasource.MovieLocalDataSource
import com.enzoroiz.tmdb.data.repository.movie.datasource.MovieRemoteDataSource
import com.enzoroiz.tmdb.data.repository.tvshow.TVShowRepositoryImpl
import com.enzoroiz.tmdb.data.repository.tvshow.datasource.TVShowCacheDataSource
import com.enzoroiz.tmdb.data.repository.tvshow.datasource.TVShowLocalDataSource
import com.enzoroiz.tmdb.data.repository.tvshow.datasource.TVShowRemoteDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideMovieRepository(
        cacheDataSource: MovieCacheDataSource,
        localDataSource: MovieLocalDataSource,
        remoteDataSource: MovieRemoteDataSource
    ): MovieRepository {
        return MovieRepositoryImpl(cacheDataSource, localDataSource, remoteDataSource)
    }

    @Singleton
    @Provides
    fun provideTVShowRepository(
        cacheDataSource: TVShowCacheDataSource,
        localDataSource: TVShowLocalDataSource,
        remoteDataSource: TVShowRemoteDataSource
    ): TVShowRepository {
        return TVShowRepositoryImpl(cacheDataSource, localDataSource, remoteDataSource)
    }

    @Singleton
    @Provides
    fun provideArtistRepository(
        cacheDataSource: ArtistCacheDataSource,
        localDataSource: ArtistLocalDataSource,
        remoteDataSource: ArtistRemoteDataSource
    ): ArtistsRepository {
        return ArtistRepositoryImpl(cacheDataSource, localDataSource, remoteDataSource)
    }
}