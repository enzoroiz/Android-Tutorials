package com.enzoroiz.tmdb.presentation.di.artist

import com.enzoroiz.tmdb.presentation.artist.ArtistsActivity
import dagger.Subcomponent

@ArtistScope
@Subcomponent(modules = [ArtistModule::class])
interface ArtistSubComponent {
    fun inject(activity: ArtistsActivity)

    @Subcomponent.Factory
    interface Factory {
        fun create(): ArtistSubComponent
    }
}