package com.enzoroiz.newsapp.domain.usecase

import com.enzoroiz.newsapp.data.model.Article
import com.enzoroiz.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetSavedArticlesUseCase(private val repository: NewsRepository) {
    fun execute(): Flow<List<Article>> {
        return repository.getSavedArticles()
    }
}