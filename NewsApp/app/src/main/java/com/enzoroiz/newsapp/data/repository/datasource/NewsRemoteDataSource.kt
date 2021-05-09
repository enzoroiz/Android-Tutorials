package com.enzoroiz.newsapp.data.repository.datasource

import com.enzoroiz.newsapp.data.model.NewsResponse
import retrofit2.Response

interface NewsRemoteDataSource {
    suspend fun getNewsHeadlines(page: Int, country: String): Response<NewsResponse>
    suspend fun searchNews(page: Int, country: String, searchQuery: String): Response<NewsResponse>
}