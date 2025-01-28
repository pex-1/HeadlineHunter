package com.example.headlinehunter.core.domain

import com.example.headlinehunter.core.domain.article.Article
import com.example.headlinehunter.core.domain.channel.Channel
import com.example.headlinehunter.core.domain.util.DataError
import com.example.headlinehunter.core.domain.util.EmptyResult
import com.example.headlinehunter.core.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface RssFeedRepository {

    suspend fun fetchRssChannel(channelLink: String): Result<Channel, DataError.Network>

    fun getChannels(): Flow<List<Channel>>
    suspend fun getChannelById(channelId: Int): Channel?
    suspend fun getSubscribedChannels(): List<Channel>
    suspend fun addChannel(channel: Channel): EmptyResult<DataError>
    suspend fun removeChannel(id: Int)
    suspend fun channelExists(channelLink: String): Boolean
    suspend fun updateChannel(channel: Channel)

    fun getArticles(channelIds: List<Int>): Flow<List<Article>>
    fun getFavoriteArticles(): Flow<List<Article>>
    fun getUnreadArticles(): Flow<List<Article>>
    suspend fun getArticleById(articleId: Int): Article
    suspend fun updateArticle(article: Article)
    suspend fun addArticles(articles: List<Article>)
    suspend fun fetchArticles(channelLink: String, channelId: Int): Result<Int, DataError>
    suspend fun removeArticle(article: Article)

    suspend fun updateNotifications(notificationsEnabled: Boolean)
    suspend fun setTheme(isDarkMode: Boolean)
    fun getTheme() : Flow<Boolean>
    fun notificationsEnabled(): Flow<Boolean>
}