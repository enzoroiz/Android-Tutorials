package com.enzoroiz.tmdb.presentation.di.movie

import com.enzoroiz.tmdb.data.domain.usecase.GetMoviesUseCase
import com.enzoroiz.tmdb.data.domain.usecase.UpdateMoviesUseCase
import com.enzoroiz.tmdb.presentation.movie.MovieViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class MovieModule {

    @MovieScope
    @Provides
    fun provideMovieViewModelFactory(
        getUseCase: GetMoviesUseCase,
        updateUseCase: UpdateMoviesUseCase
    ): MovieViewModelFactory {
        return MovieViewModelFactory(getUseCase, updateUseCase)
    }
}