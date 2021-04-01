package com.enzoroiz.tmdb.presentation.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.enzoroiz.tmdb.data.domain.usecase.GetMoviesUseCase
import com.enzoroiz.tmdb.data.domain.usecase.UpdateMoviesUseCase

class MovieViewModelFactory(
    private val getUseCase: GetMoviesUseCase,
    private val updateUseCase: UpdateMoviesUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        modelClass.cast(MovieViewModel(getUseCase, updateUseCase))
            ?: throw(IllegalArgumentException("Could not cast class to MovieViewModel"))
}