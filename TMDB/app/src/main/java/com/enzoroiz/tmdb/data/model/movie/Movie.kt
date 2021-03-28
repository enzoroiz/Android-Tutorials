package com.enzoroiz.tmdb.data.model.movie

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie")
data class Movie(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val overview: String,
    val poster_path: String,
    val release_date: String,
    val title: String
)