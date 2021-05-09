package com.enzoroiz.newsapp.presentation.di

import com.enzoroiz.newsapp.domain.repository.NewsRepository
import com.enzoroiz.newsapp.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
    @Singleton
    @Provides
    fun provideGetNewsHeadlinesUseCaseModule(repository: NewsRepository): GetNewsHeadlinesUseCase {
        return GetNewsHeadlinesUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetSearchedNewsUseCaseModule(repository: NewsRepository): GetSearchedNewsUseCase {
        return GetSearchedNewsUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetSavedArticlesUseCaseModule(repository: NewsRepository): GetSavedArticlesUseCase {
        return GetSavedArticlesUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideSaveArticleUseCaseModule(repository: NewsRepository): SaveArticleUseCase {
        return SaveArticleUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideDeleteSavedArticlesUseCaseModule(repository: NewsRepository): DeleteSavedArticlesUseCase {
        return DeleteSavedArticlesUseCase(repository)
    }
}