package com.enzoroiz.newsapp.data.repository.datasourceimplementation

import com.enzoroiz.newsapp.data.api.NewsService
import com.enzoroiz.newsapp.data.model.NewsResponse
import com.enzoroiz.newsapp.data.repository.datasource.NewsRemoteDataSource
import retrofit2.Response

class NewsRemoteDataSourceImpl(
    private val service: NewsService,
    private val page: Int,
    private val country: String
): NewsRemoteDataSource {
    override suspend fun getNewsHeadlines(): Response<NewsResponse> {
        return service.getNewsHeadlines(page, country)
    }

    override suspend fun searchNews(searchQuery: String): Response<NewsResponse> {
        return service.searchNews(searchQuery)
    }

}