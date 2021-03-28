package com.enzoroiz.tmdb.data.model.tvshow

import com.google.gson.annotations.SerializedName

data class TVShowList(
    @SerializedName("results")
    val tvShows: List<TVShow>
)