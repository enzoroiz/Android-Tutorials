package com.enzoroiz.newsapp.data.repository.datasourceimplementation

import com.enzoroiz.newsapp.data.database.AppDatabase
import com.enzoroiz.newsapp.data.model.Article
import com.enzoroiz.newsapp.data.repository.datasource.NewsLocalDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsLocalDataSourceImpl @Inject constructor(appDatabase: AppDatabase): NewsLocalDataSource {
    private val articleDAO = appDatabase.getArticleDAO()

    override fun getSavedArticles(): Flow<List<Article>> {
        return articleDAO.getSavedArticles()
    }

    override suspend fun saveArticle(article: Article) {
        articleDAO.saveArticle(article)
    }

    override suspend fun deleteSavedArticle(article: Article): Int {
        return articleDAO.deleteSavedArticle(article)
    }

}