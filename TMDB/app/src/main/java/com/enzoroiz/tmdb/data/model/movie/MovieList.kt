package com.enzoroiz.tmdb.data.model.movie

import com.google.gson.annotations.SerializedName

class MovieList(
    @SerializedName("results")
    val movies: List<Movie>
)