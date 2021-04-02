package com.enzoroiz.tmdb.presentation.di.artist

import com.enzoroiz.tmdb.data.domain.usecase.GetArtistsUseCase
import com.enzoroiz.tmdb.data.domain.usecase.UpdateArtistsUseCase
import com.enzoroiz.tmdb.presentation.artist.ArtistViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ArtistModule {

    @ArtistScope
    @Provides
    fun provideArtistViewModelFactory(
        getUseCase: GetArtistsUseCase,
        updateUseCase: UpdateArtistsUseCase
    ): ArtistViewModelFactory {
        return ArtistViewModelFactory(getUseCase, updateUseCase)
    }
}