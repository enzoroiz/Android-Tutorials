package com.enzoroiz.newsapp.domain.repository

import com.enzoroiz.newsapp.data.model.Article
import com.enzoroiz.newsapp.data.model.NewsResponse
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getNewsHeadlines(page: Int, country: String): Resource<NewsResponse>
    suspend fun searchNews(page: Int, country: String, searchQuery: String): Resource<NewsResponse>
    suspend fun saveArticle(article: Article)
    suspend fun deleteSavedArticle(article: Article)
    fun getSavedArticles(): Flow<List<Article>>
}