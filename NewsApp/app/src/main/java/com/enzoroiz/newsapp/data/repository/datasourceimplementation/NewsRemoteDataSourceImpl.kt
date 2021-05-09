package com.enzoroiz.newsapp.data.repository.datasourceimplementation

import com.enzoroiz.newsapp.data.api.NewsService
import com.enzoroiz.newsapp.data.model.NewsResponse
import com.enzoroiz.newsapp.data.repository.datasource.NewsRemoteDataSource
import retrofit2.Response

class NewsRemoteDataSourceImpl(
    private val service: NewsService
): NewsRemoteDataSource {
    override suspend fun getNewsHeadlines(page: Int, country: String): Response<NewsResponse> {
        return service.getNewsHeadlines(page, country)
    }

    override suspend fun searchNews(page: Int, country: String, searchQuery: String): Response<NewsResponse> {
        return service.searchNews(page, country, searchQuery)
    }

}