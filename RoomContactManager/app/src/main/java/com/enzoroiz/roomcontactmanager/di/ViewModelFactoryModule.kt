package com.enzoroiz.roomcontactmanager.di

import com.enzoroiz.roomcontactmanager.repository.ContactsRepository
import com.enzoroiz.roomcontactmanager.viewmodel.ContactsViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ViewModelFactoryModule {
    @Singleton
    @Provides
    fun provideViewModelFactory(repository: ContactsRepository): ContactsViewModelFactory {
        return ContactsViewModelFactory(repository)
    }
}