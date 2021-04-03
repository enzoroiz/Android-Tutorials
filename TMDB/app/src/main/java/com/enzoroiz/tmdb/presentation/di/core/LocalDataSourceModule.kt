package com.enzoroiz.tmdb.presentation.di.core

import com.enzoroiz.tmdb.data.database.dao.ArtistDAO
import com.enzoroiz.tmdb.data.database.dao.MovieDAO
import com.enzoroiz.tmdb.data.database.dao.TVShowDAO
import com.enzoroiz.tmdb.data.repository.artist.datasource.ArtistLocalDataSource
import com.enzoroiz.tmdb.data.repository.artist.datasourceimplementation.ArtistLocalDataSourceImpl
import com.enzoroiz.tmdb.data.repository.movie.datasource.MovieLocalDataSource
import com.enzoroiz.tmdb.data.repository.movie.datasourceimplementation.MovieLocalDataSourceImpl
import com.enzoroiz.tmdb.data.repository.tvshow.datasource.TVShowLocalDataSource
import com.enzoroiz.tmdb.data.repository.tvshow.datasourceimplementation.TVShowLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocalDataSourceModule {

    @Singleton
    @Provides
    fun provideMovieLocalDataSource(movieDAO: MovieDAO): MovieLocalDataSource {
        return MovieLocalDataSourceImpl(movieDAO)
    }

    @Singleton
    @Provides
    fun provideTVShowLocalDataSource(tvShowDAO: TVShowDAO): TVShowLocalDataSource {
        return TVShowLocalDataSourceImpl(tvShowDAO)
    }

    @Singleton
    @Provides
    fun provideArtistLocalDataSource(artistDAO: ArtistDAO): ArtistLocalDataSource {
        return ArtistLocalDataSourceImpl(artistDAO)
    }
}