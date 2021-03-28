package com.enzoroiz.tmdb.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.enzoroiz.tmdb.data.model.tvshow.TVShow

@Dao
interface TVShowDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTVShows(tvShows: List<TVShow>)

    @Query("DELETE FROM tv_show")
    suspend fun deleteAllTVShows()

    @Query("SELECT * FROM tv_show")
    suspend fun getTVShows(): List<TVShow>
}