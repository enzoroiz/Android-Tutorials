package com.enzoroiz.tmdb.data.model.tvshow

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tv_show")
data class TVShow(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val first_air_date: String,
    val name: String,
    val overview: String,
    val poster_path: String
)