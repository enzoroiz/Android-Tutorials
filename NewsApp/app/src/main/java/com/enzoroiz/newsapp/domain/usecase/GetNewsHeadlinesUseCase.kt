package com.enzoroiz.newsapp.domain.usecase

import com.enzoroiz.newsapp.data.model.NewsResponse
import com.enzoroiz.newsapp.domain.repository.Resource
import com.enzoroiz.newsapp.domain.repository.NewsRepository

class GetNewsHeadlinesUseCase(private val repository: NewsRepository) {
    suspend fun execute(): Resource<NewsResponse> {
        return repository.getNewsHeadlines()
    }
}