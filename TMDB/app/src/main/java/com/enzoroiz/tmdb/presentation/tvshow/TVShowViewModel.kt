package com.enzoroiz.tmdb.presentation.tvshow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.enzoroiz.tmdb.data.domain.usecase.GetMoviesUseCase
import com.enzoroiz.tmdb.data.domain.usecase.GetTVShowsUseCase
import com.enzoroiz.tmdb.data.domain.usecase.UpdateMoviesUseCase
import com.enzoroiz.tmdb.data.domain.usecase.UpdateTVShowsUseCase

class TVShowViewModel(private val getUseCase: GetTVShowsUseCase, private val updateUseCase: UpdateTVShowsUseCase) : ViewModel() {

    fun getTVShows() = liveData {
        emit(getUseCase.execute())
    }

    fun updateTVShows() = liveData {
        emit(updateUseCase.execute())
    }
}