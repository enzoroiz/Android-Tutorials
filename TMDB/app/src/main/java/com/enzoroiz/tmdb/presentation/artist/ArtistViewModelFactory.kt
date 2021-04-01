package com.enzoroiz.tmdb.presentation.artist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.enzoroiz.tmdb.data.domain.usecase.GetArtistsUseCase
import com.enzoroiz.tmdb.data.domain.usecase.UpdateArtistsUseCase

class ArtistViewModelFactory(
    private val getUseCase: GetArtistsUseCase,
    private val updateUseCase: UpdateArtistsUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        modelClass.cast(ArtistViewModel(getUseCase, updateUseCase))
            ?: throw(IllegalArgumentException("Could not cast class to ArtistViewModel"))
}