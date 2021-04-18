package com.enzoroiz.newsapp.presentation.di

import com.enzoroiz.newsapp.data.api.NewsService
import com.enzoroiz.newsapp.data.repository.datasource.NewsRemoteDataSource
import com.enzoroiz.newsapp.data.repository.datasourceimplementation.NewsRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteDataSourceModule {
    @Singleton
    @Provides
    fun provideNewsRemoteDataSource(service: NewsService): NewsRemoteDataSource {
        return NewsRemoteDataSourceImpl(service)
    }
}
