package com.enzoroiz.newsapp.presentation.di

import com.enzoroiz.newsapp.data.repository.NewsRepositoryImpl
import com.enzoroiz.newsapp.data.repository.datasource.NewsRemoteDataSource
import com.enzoroiz.newsapp.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Singleton
    @Provides
    fun provideNewsRepository(remoteDataSource: NewsRemoteDataSource): NewsRepository {
        return NewsRepositoryImpl(remoteDataSource)
    }
}