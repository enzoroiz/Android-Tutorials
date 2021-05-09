package com.enzoroiz.newsapp.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.enzoroiz.newsapp.domain.usecase.*

class NewsViewModelFactory(
    private val application: Application,
    private val getNewsHeadlinesUseCase: GetNewsHeadlinesUseCase,
    private val getSearchedNewsUseCase: GetSearchedNewsUseCase,
    private val getSavedArticlesUseCase: GetSavedArticlesUseCase,
    private val saveArticleUseCase: SaveArticleUseCase,
    private val deleteSavedArticlesUseCase: DeleteSavedArticlesUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        modelClass.cast(
            NewsViewModel(
                application,
                getNewsHeadlinesUseCase,
                getSearchedNewsUseCase,
                getSavedArticlesUseCase,
                saveArticleUseCase,
                deleteSavedArticlesUseCase
            )
        ) ?: throw IllegalArgumentException("Cannot cast object to NewsViewModel class")
}