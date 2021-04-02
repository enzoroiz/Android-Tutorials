package com.enzoroiz.tmdb.presentation.di

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        CacheDataSourceModule::class,
        LocalDataSourceModule::class,
        RemoteDataSourceModule::class,
        DatabaseModule::class,
        NetworkModule::class,
        RepositoryModule::class,
        UseCaseModule::class
    ]
)
interface AppComponent { }