package com.enzoroiz.tmdb.presentation.di.core

import com.enzoroiz.tmdb.presentation.di.artist.ArtistSubComponent
import com.enzoroiz.tmdb.presentation.di.movie.MovieSubComponent
import com.enzoroiz.tmdb.presentation.di.tvshow.TVShowSubComponent

interface Injector {
    fun createMovieSubComponent(): MovieSubComponent
    fun createTVShowSubComponent(): TVShowSubComponent
    fun createArtistSubComponent(): ArtistSubComponent
}