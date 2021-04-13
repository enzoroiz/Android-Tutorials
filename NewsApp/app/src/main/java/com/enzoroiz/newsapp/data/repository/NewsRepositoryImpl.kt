package com.enzoroiz.newsapp.data.repository

import com.enzoroiz.newsapp.data.model.Article
import com.enzoroiz.newsapp.data.model.NewsResponse
import com.enzoroiz.newsapp.data.repository.datasource.NewsRemoteDataSource
import com.enzoroiz.newsapp.domain.repository.NewsRepository
import com.enzoroiz.newsapp.domain.repository.Resource
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class NewsRepositoryImpl(
    private val remoteDataSource: NewsRemoteDataSource
): NewsRepository {
    override suspend fun getNewsHeadlines(page: Int, country: String): Resource<NewsResponse> {
        return fromResponseToResource(remoteDataSource.getNewsHeadlines(page, country))
    }

    override suspend fun searchNews(searchQuery: String): Resource<NewsResponse> {
        return fromResponseToResource(remoteDataSource.searchNews(searchQuery))
    }

    override suspend fun saveArticle(article: Article) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteArticle(article: Article) {
        TODO("Not yet implemented")
    }

    override fun getSavedArticles(): Flow<List<Article>> {
        TODO("Not yet implemented")
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