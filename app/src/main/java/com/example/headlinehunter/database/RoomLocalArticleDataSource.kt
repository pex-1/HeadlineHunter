package com.example.headlinehunter.database

import android.database.sqlite.SQLiteFullException
import com.example.headlinehunter.core.domain.article.Article
import com.example.headlinehunter.core.domain.article.LocalArticleDataSource
import com.example.headlinehunter.core.domain.util.DataError
import com.example.headlinehunter.core.domain.util.Result
import com.example.headlinehunter.database.dao.ArticleDao
import com.example.headlinehunter.database.mappers.toArticle
import com.example.headlinehunter.database.mappers.toArticleEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.Int

class RoomLocalArticleDataSource(
    private val articleDao: ArticleDao
) : LocalArticleDataSource {

    override fun getArticles(channelIds: List<Int>): Flow<List<Article>> {
        return articleDao.getArticles(channelIds)
            .map { articles ->
                articles.map {
                    it.toArticle()
                }
            }
    }

    override suspend fun getAllArticles(): List<Article> {
        return articleDao.getAllArticles().map { it.toArticle() }
    }

    override suspend fun getArticleById(articleId: Int): Article {
        return articleDao.getArticleById(articleId)
    }

    override fun getFavoriteArticles(): Flow<List<Article>> {
        return articleDao.getFavoriteArticles().map { articles ->
            articles.map { it.toArticle() }
        }
    }

    override fun getUnreadArticles(): Flow<List<Article>> {
        return articleDao.getUnreadArticles().map { articles ->
            articles.map { it.toArticle() }
        }
    }

    override suspend fun updateArticle(article: Article) {
        articleDao.updateArticle(article.toArticleEntity())
    }

    override suspend fun upsertArticles(articles: List<Article>, channelId: Int?):
            Result<List<Int>, DataError.Local> {
        return try {
            val entities = articles.map { it.toArticleEntity(channelId) }
            articleDao.upsertArticles(entities)
            Result.Success(entities.map { it.pubDate.toInt() })
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun removeArticle(article: Article) {
        articleDao.removeArticle(article.toArticleEntity())
    }


}