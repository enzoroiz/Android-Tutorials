package com.enzoroiz.newsapp.domain.usecase

import com.enzoroiz.newsapp.data.model.NewsResponse
import com.enzoroiz.newsapp.domain.repository.Resource
import com.enzoroiz.newsapp.domain.repository.NewsRepository

class GetSearchedNewsUseCase (private val repository: NewsRepository) {
    suspend fun execute(page: Int, country: String, searchQuery: String): Resource<NewsResponse> {
        return repository.searchNews(page, country, searchQuery)
    }
}