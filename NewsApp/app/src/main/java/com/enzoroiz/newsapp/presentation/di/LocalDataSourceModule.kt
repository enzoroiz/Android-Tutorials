package com.enzoroiz.newsapp.presentation.di

import com.enzoroiz.newsapp.data.database.AppDatabase
import com.enzoroiz.newsapp.data.repository.datasource.NewsLocalDataSource
import com.enzoroiz.newsapp.data.repository.datasourceimplementation.NewsLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDataSourceModule {

    @Singleton
    @Provides
    fun provideLocalDataSource(appDatabase: AppDatabase): NewsLocalDataSource {
        return NewsLocalDataSourceImpl(appDatabase)
    }
}