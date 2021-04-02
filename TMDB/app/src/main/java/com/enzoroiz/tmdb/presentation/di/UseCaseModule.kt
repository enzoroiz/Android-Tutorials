package com.enzoroiz.tmdb.presentation.di

import com.enzoroiz.tmdb.data.domain.repository.ArtistsRepository
import com.enzoroiz.tmdb.data.domain.repository.MovieRepository
import com.enzoroiz.tmdb.data.domain.repository.TVShowRepository
import com.enzoroiz.tmdb.data.domain.usecase.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UseCaseModule {

    @Singleton
    @Provides
    fun provideGetMoviesUseCaseModule(repository: MovieRepository): GetMoviesUseCase {
        return GetMoviesUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideUpdateMoviesUseCaseModule(repository: MovieRepository): UpdateMoviesUseCase {
        return UpdateMoviesUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetTVShowsUseCaseModule(repository: TVShowRepository): GetTVShowsUseCase {
        return GetTVShowsUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideUpdateTVShowsUseCaseModule(repository: TVShowRepository): UpdateTVShowsUseCase {
        return UpdateTVShowsUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetArtistsUseCaseModule(repository: ArtistsRepository): GetArtistsUseCase {
        return GetArtistsUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideUpdateArtistsUseCaseModule(repository: ArtistsRepository): UpdateArtistsUseCase {
        return UpdateArtistsUseCase(repository)
    }
}