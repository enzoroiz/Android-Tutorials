package com.enzoroiz.newsapp.data.database

import androidx.room.*
import com.enzoroiz.newsapp.data.model.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveArticle(article: Article)

    @Delete
    suspend fun deleteSavedArticle(article: Article): Int

    @Query("SELECT * FROM articles")
    fun getSavedArticles(): Flow<List<Article>>
}