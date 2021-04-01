package com.enzoroiz.tmdb.presentation.artist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.enzoroiz.tmdb.data.domain.usecase.GetArtistsUseCase
import com.enzoroiz.tmdb.data.domain.usecase.UpdateArtistsUseCase

class ArtistViewModel(private val getUseCase: GetArtistsUseCase, private val updateUseCase: UpdateArtistsUseCase) : ViewModel() {

    fun getArtists() = liveData {
        emit(getUseCase.execute())
    }

    fun updateArtists() = liveData {
        emit(updateUseCase.execute())
    }
}