package com.example.headlinehunter.core.domain.article

import com.example.headlinehunter.core.domain.util.DataError
import com.example.headlinehunter.core.domain.util.Result
import kotlinx.coroutines.flow.Flow

typealias ArticleId = Int

interface LocalArticleDataSource {

    fun getArticles(channelIds: List<Int>): Flow<List<Article>>
    suspend fun getAllArticles(): List<Article>
    suspend fun getArticleById(articleId: Int): Article
    fun getFavoriteArticles(): Flow<List<Article>>
    fun getUnreadArticles(): Flow<List<Article>>
    suspend fun updateArticle(article: Article)
    suspend fun upsertArticles(articles: List<Article>, channelId: Int? = null): Result<List<ArticleId>, DataError.Local>
    suspend fun removeArticle(article: Article)
}