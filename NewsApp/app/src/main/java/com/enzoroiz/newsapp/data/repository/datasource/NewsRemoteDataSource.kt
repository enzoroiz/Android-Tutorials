package com.enzoroiz.newsapp.data.repository.datasource

import com.enzoroiz.newsapp.data.model.NewsResponse
import retrofit2.Response

interface NewsRemoteDataSource {
    suspend fun getNewsHeadlines(): Response<NewsResponse>
    suspend fun searchNews(searchQuery: String): Response<NewsResponse>
}