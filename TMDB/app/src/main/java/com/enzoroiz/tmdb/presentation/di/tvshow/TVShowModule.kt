package com.enzoroiz.tmdb.presentation.di.tvshow

import com.enzoroiz.tmdb.data.domain.usecase.GetTVShowsUseCase
import com.enzoroiz.tmdb.data.domain.usecase.UpdateTVShowsUseCase
import com.enzoroiz.tmdb.presentation.tvshow.TVShowViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class TVShowModule {

    @TVShowScope
    @Provides
    fun provideTVShowViewModelFactory(
        getUseCase: GetTVShowsUseCase,
        updateUseCase: UpdateTVShowsUseCase
    ): TVShowViewModelFactory {
        return TVShowViewModelFactory(getUseCase, updateUseCase)
    }
}