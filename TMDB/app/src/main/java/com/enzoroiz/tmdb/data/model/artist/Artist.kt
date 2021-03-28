package com.enzoroiz.tmdb.data.model.artist

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "artist")
data class Artist(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val popularity: Double,
    val profile_path: String
)