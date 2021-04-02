package com.enzoroiz.tmdb.presentation.di.core

import com.enzoroiz.tmdb.presentation.di.artist.ArtistSubComponent
import com.enzoroiz.tmdb.presentation.di.movie.MovieSubComponent
import com.enzoroiz.tmdb.presentation.di.tvshow.TVShowSubComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        CacheDataSourceModule::class,
        LocalDataSourceModule::class,
        RemoteDataSourceModule::class,
        DatabaseModule::class,
        NetworkModule::class,
        RepositoryModule::class,
        UseCaseModule::class
    ]
)
interface AppComponent {
    fun movieSubComponent(): MovieSubComponent.Factory
    fun tvShowSubComponent(): TVShowSubComponent.Factory
    fun artistSubComponent(): ArtistSubComponent.Factory
}