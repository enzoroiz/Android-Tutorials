package com.enzoroiz.tmdb.presentation.tvshow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.enzoroiz.tmdb.data.domain.usecase.GetTVShowsUseCase
import com.enzoroiz.tmdb.data.domain.usecase.UpdateTVShowsUseCase

class TVShowViewModelFactory(
    private val getUseCase: GetTVShowsUseCase,
    private val updateUseCase: UpdateTVShowsUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        modelClass.cast(TVShowViewModel(getUseCase, updateUseCase))
            ?: throw(IllegalArgumentException("Could not cast class to TVShowViewModel"))
}