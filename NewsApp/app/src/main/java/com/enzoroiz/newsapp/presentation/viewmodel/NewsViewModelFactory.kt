package com.enzoroiz.newsapp.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.enzoroiz.newsapp.domain.usecase.GetNewsHeadlinesUseCase
import com.enzoroiz.newsapp.domain.usecase.GetSearchedNewsUseCase

class NewsViewModelFactory(
    private val application: Application,
    private val getNewsHeadlinesUseCase: GetNewsHeadlinesUseCase,
    private val getSearchedNewsUseCase: GetSearchedNewsUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        modelClass.cast(NewsViewModel(application, getNewsHeadlinesUseCase, getSearchedNewsUseCase))
            ?: throw IllegalArgumentException("Cannot cast object to NewsViewModel class")
}