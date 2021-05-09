package com.enzoroiz.newsapp.data.repository.datasource

import com.enzoroiz.newsapp.data.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsLocalDataSource {
    fun getSavedArticles(): Flow<List<Article>>
    suspend fun saveArticle(article: Article)
    suspend fun deleteSavedArticle(article: Article): Int
}