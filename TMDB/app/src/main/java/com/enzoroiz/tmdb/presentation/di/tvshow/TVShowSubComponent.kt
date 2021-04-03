package com.enzoroiz.tmdb.presentation.di.tvshow

import com.enzoroiz.tmdb.presentation.tvshow.TVShowsActivity
import dagger.Subcomponent

@TVShowScope
@Subcomponent(modules = [TVShowModule::class])
interface TVShowSubComponent {
    fun inject(activity: TVShowsActivity)

    @Subcomponent.Factory
    interface Factory {
        fun create(): TVShowSubComponent
    }
}