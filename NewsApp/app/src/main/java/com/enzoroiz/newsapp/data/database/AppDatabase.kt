package com.enzoroiz.newsapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.enzoroiz.newsapp.data.model.Article

@TypeConverters(Converters::class)
@Database(
    version = AppDatabase.DATABASE_VERSION,
    entities = [Article::class],
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    companion object {
        const val DATABASE_NAME = "app_database"
        const val DATABASE_VERSION = 2
    }

    abstract fun getArticleDAO(): ArticleDAO
}