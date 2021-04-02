package com.enzoroiz.tmdb.presentation

import android.app.Application
import com.enzoroiz.tmdb.BuildConfig
import com.enzoroiz.tmdb.presentation.di.artist.ArtistSubComponent
import com.enzoroiz.tmdb.presentation.di.core.*
import com.enzoroiz.tmdb.presentation.di.movie.MovieSubComponent
import com.enzoroiz.tmdb.presentation.di.tvshow.TVShowSubComponent

class Application: Application(), Injector {
    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(baseContext))
            .networkModule(NetworkModule(BuildConfig.BASE_URL))
            .remoteDataSourceModule(RemoteDataSourceModule(BuildConfig.API_KEY))
            .build()
    }

    override fun createMovieSubComponent(): MovieSubComponent {
        return appComponent.movieSubComponent().create()
    }

    override fun createTVShowSubComponent(): TVShowSubComponent {
        return appComponent.tvShowSubComponent().create()
    }

    override fun createArtistSubComponent(): ArtistSubComponent {
        return appComponent.artistSubComponent().create()
    }
}