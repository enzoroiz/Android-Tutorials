package com.enzoroiz.newsapp.data.api

import com.enzoroiz.newsapp.BuildConfig
import com.enzoroiz.newsapp.data.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("v2/top-headlines")
    suspend fun getNewsHeadlines(
        @Query("page") page: Int,
        @Query("country") country: String,
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY
    ): Response<NewsResponse>

    @GET("v2/top-headlines")
    suspend fun searchNews(
        @Query("q") searchQuery: String,
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY
    ): Response<NewsResponse>
}