package com.enzoroiz.roomdatabase.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SubscriberDAO {
    @Insert
    suspend fun createSubscriber(subscriber: Subscriber): Long

    @Update
    suspend fun updateSubscriber(subscriber: Subscriber): Int

    @Delete
    suspend fun deleteSubscriber(subscriber: Subscriber): Int

    @Query("DELETE FROM ${Subscriber.TABLE_NAME}")
    suspend fun deleteAll(): Int

    @Query("SELECT * FROM ${Subscriber.TABLE_NAME}")
    fun getAll(): LiveData<List<Subscriber>>
}