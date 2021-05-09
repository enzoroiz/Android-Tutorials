package com.enzoroiz.newsapp.data.repository

import com.enzoroiz.newsapp.data.model.Article
import com.enzoroiz.newsapp.data.model.NewsResponse
import com.enzoroiz.newsapp.data.repository.datasource.NewsLocalDataSource
import com.enzoroiz.newsapp.data.repository.datasource.NewsRemoteDataSource
import com.enzoroiz.newsapp.domain.repository.NewsRepository
import com.enzoroiz.newsapp.domain.repository.Resource
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class NewsRepositoryImpl(
    private val remoteDataSource: NewsRemoteDataSource,
    private val localDataSource: NewsLocalDataSource
): NewsRepository {
    override suspend fun getNewsHeadlines(page: Int, country: String): Resource<NewsResponse> {
        return fromResponseToResource(remoteDataSource.getNewsHeadlines(page, country))
    }

    override suspend fun searchNews(page: Int, country: String, searchQuery: String): Resource<NewsResponse> {
        return fromResponseToResource(remoteDataSource.searchNews(page, country, searchQuery))
    }

    override suspend fun saveArticle(article: Article) {
        localDataSource.saveArticle(article)
    }

    override suspend fun deleteSavedArticle(article: Article) {
        localDataSource.deleteSavedArticle(article)
    }

    override fun getSavedArticles(): Flow<List<Article>> {
        return localDataSource.getSavedArticles()
    }

    private fun fromResponseToResource(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }

        return Resource.Error(response.message())
    }
}