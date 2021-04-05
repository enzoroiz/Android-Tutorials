package com.enzoroiz.newsapp.domain.repository

import com.enzoroiz.newsapp.data.model.Article
import com.enzoroiz.newsapp.data.model.NewsResponse
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getNewsHeadlines(): Resource<NewsResponse>
    suspend fun searchNews(searchQuery: String): Resource<NewsResponse>
    suspend fun saveArticle(article: Article)
    suspend fun deleteArticle(article: Article)
    fun getSavedArticles(): Flow<List<Article>>
}