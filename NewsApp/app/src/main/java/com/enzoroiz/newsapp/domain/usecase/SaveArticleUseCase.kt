package com.enzoroiz.newsapp.domain.usecase

import com.enzoroiz.newsapp.data.model.Article
import com.enzoroiz.newsapp.domain.repository.NewsRepository

class SaveArticleUseCase(private val repository: NewsRepository) {
    suspend fun execute(article: Article) {
        repository.saveArticle(article)
    }
}