package com.example.headlinehunter.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.headlinehunter.core.domain.article.Article
import com.example.headlinehunter.database.entity.ArticleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {

    @Query("SELECT * FROM article_table WHERE channelId IN (:channelIds) ORDER BY pubDate DESC")
    fun getArticles(channelIds: List<Int>): Flow<List<ArticleEntity>>

    @Query("SELECT * FROM article_table WHERE id = :articleId")
    suspend fun getArticleById(articleId: Int): Article

    @Query("DELETE FROM article_table WHERE channelId = :channelId AND isFavorite = 0")
    fun removeArticles(channelId: Int)

    @Query("SELECT * FROM article_table")
    suspend fun getAllArticles(): List<ArticleEntity>

    @Query("SELECT * FROM article_table WHERE isFavorite = 1 ORDER BY pubDate DESC")
    fun getFavoriteArticles(): Flow<List<ArticleEntity>>

    @Query("SELECT * FROM article_table WHERE isArticleRead = 0 ORDER BY pubDate DESC")
    fun getUnreadArticles(): Flow<List<ArticleEntity>>

    @Upsert
    suspend fun upsertArticles(articles: List<ArticleEntity>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateArticle(articleEntity: ArticleEntity)

    @Delete
    suspend fun removeArticle(article: ArticleEntity)
}