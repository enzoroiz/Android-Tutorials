package com.enzoroiz.tmdb.presentation.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.enzoroiz.tmdb.data.domain.usecase.GetMoviesUseCase
import com.enzoroiz.tmdb.data.domain.usecase.UpdateMoviesUseCase

class MovieViewModel(private val getUseCase: GetMoviesUseCase, private val updateUseCase: UpdateMoviesUseCase) : ViewModel() {

    fun getMovies() = liveData {
        emit(getUseCase.execute())
    }

    fun updateMovies() = liveData {
        emit(updateUseCase.execute())
    }
}