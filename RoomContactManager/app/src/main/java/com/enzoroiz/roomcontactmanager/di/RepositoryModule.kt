package com.enzoroiz.roomcontactmanager.di

import com.enzoroiz.roomcontactmanager.database.AppDatabase
import com.enzoroiz.roomcontactmanager.repository.ContactsRepository
import com.enzoroiz.roomcontactmanager.repository.LocalDataSource
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
    fun provideLocalDataSource(database: AppDatabase): LocalDataSource {
        return LocalDataSource(database)
    }

    @Singleton
    @Provides
    fun provideRepository(localDataSource: LocalDataSource): ContactsRepository {
        return ContactsRepository(localDataSource)
    }
}