package com.enzoroiz.tmdb.presentation.di.core

import android.content.Context
import com.enzoroiz.tmdb.presentation.di.artist.ArtistSubComponent
import com.enzoroiz.tmdb.presentation.di.movie.MovieSubComponent
import com.enzoroiz.tmdb.presentation.di.tvshow.TVShowSubComponent
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(subcomponents = [MovieSubComponent::class, TVShowSubComponent::class, ArtistSubComponent::class])
class AppModule(private val context: Context) {

    @Singleton
    @Provides
    fun provideContext(): Context {
        return context.applicationContext
    }
}