package com.enzoroiz.tmdb.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.enzoroiz.tmdb.data.model.artist.Artist

@Dao
interface ArtistDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveArtists(artists: List<Artist>)

    @Query("DELETE FROM artist")
    suspend fun deleteAllArtists()

    @Query("SELECT * FROM artist")
    suspend fun getArtists(): List<Artist>
}