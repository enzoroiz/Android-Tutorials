package com.enzoroiz.newsapp.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.enzoroiz.newsapp.domain.usecase.GetNewsHeadlinesUseCase

class NewsViewModelFactory(
    private val application: Application,
    private val useCase: GetNewsHeadlinesUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        modelClass.cast(NewsViewModel(application, useCase))
            ?: throw IllegalArgumentException("Cannot cast object to NewsViewModel class")
}