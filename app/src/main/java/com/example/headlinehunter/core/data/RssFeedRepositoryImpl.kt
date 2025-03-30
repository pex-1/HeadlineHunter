package com.example.headlinehunter.core.data

import com.example.headlinehunter.core.domain.DataStorePreferences
import com.example.headlinehunter.core.domain.RemoteDataSource
import com.example.headlinehunter.core.domain.RssFeedRepository
import com.example.headlinehunter.core.domain.article.Article
import com.example.headlinehunter.core.domain.article.LocalArticleDataSource
import com.example.headlinehunter.core.domain.channel.Channel
import com.example.headlinehunter.core.domain.channel.LocalChannelDataSource
import com.example.headlinehunter.core.domain.util.DataError
import com.example.headlinehunter.core.domain.util.EmptyResult
import com.example.headlinehunter.core.domain.util.Result
import com.example.headlinehunter.core.domain.util.asEmptyDataResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class RssFeedRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localChannelDataSource: LocalChannelDataSource,
    private val localArticleDataSource: LocalArticleDataSource,
    private val applicationScope: CoroutineScope,
    private val dataStore: DataStorePreferences
) : RssFeedRepository {

    override suspend fun fetchRssChannel(channelLink: String): Result<Channel, DataError.Network> {
        return remoteDataSource.fetchRssChannel(channelLink)
    }

    override fun getChannels(): Flow<List<Channel>> {
        return localChannelDataSource.getChannels()
    }

    override suspend fun getChannelById(channelId: Int): Channel? {
        return localChannelDataSource.getChannelById(channelId)
    }

    override suspend fun getSubscribedChannels(): List<Channel> {
        return localChannelDataSource.getSubscribedChannels()
    }

    override suspend fun addChannel(channel: Channel): EmptyResult<DataError> {
        return when (val result = localChannelDataSource.addChannel(channel)) {
            is Result.Success -> result.asEmptyDataResult()
            is Result.Error -> Result.Error(result.error)
        }
    }

    override suspend fun removeChannel(id: Int) {
        localChannelDataSource.removeChannel(id)
    }

    override suspend fun channelExists(channelLink: String): Boolean {
        return localChannelDataSource.channelExists(channelLink)
    }

    override suspend fun updateChannel(channel: Channel) {
        localChannelDataSource.updateChannel(channel)
    }

    override fun getArticles(channelIds: List<Int>): Flow<List<Article>> {
        return localArticleDataSource.getArticles(channelIds)
    }

    override fun getFavoriteArticles(): Flow<List<Article>> {
        return localArticleDataSource.getFavoriteArticles()
    }

    override fun getUnreadArticles(): Flow<List<Article>> {
        return localArticleDataSource.getUnreadArticles()
    }

    override suspend fun getArticleById(articleId: Int): Article {
        return localArticleDataSource.getArticleById(articleId)
    }

    override suspend fun updateArticle(article: Article) {
        localArticleDataSource.updateArticle(article)
    }

    override suspend fun addArticles(articles: List<Article>) {
        localArticleDataSource.upsertArticles(articles)
    }

    override suspend fun fetchArticles(
        channelLink: String,
        channelId: Int
    ): Result<Int, DataError> {
        return when (val result = remoteDataSource.fetchNewsFeed(channelLink)) {
            is Result.Success -> {
                applicationScope.async {
                    //reset isFavorite and isArticleRead to match fetched articles
                    val storedArticles = localArticleDataSource.getArticles(listOf(channelId))
                        .first().map { it.copy(isFavorite = false, isArticleRead = false) }
                    val newArticles = getUniqueArticles(result.data, storedArticles)
                    localArticleDataSource.upsertArticles(newArticles, channelId)
                    Result.Success(newArticles.size)
                }.await()
            }

            is Result.Error -> Result.Error(result.error)
        }
    }

    override suspend fun removeArticle(article: Article) {
        localArticleDataSource.removeArticle(article)
    }

    override suspend fun setNotifications(notificationsEnabled: Boolean) {
        dataStore.updateNotifications(notificationsEnabled)
    }

    override suspend fun setTheme(isDarkMode: Boolean) {
        dataStore.setTheme(isDarkMode)
    }

    override suspend fun setCollapseChannels(collapse: Boolean) {
        dataStore.setCollapseChannels(collapse)
    }

    override fun getTheme(): Flow<Boolean> {
        return dataStore.getTheme()
    }

    override fun getCollapseChannels(): Flow<Boolean> {
        return dataStore.getCollapseChannels()
    }

    override fun notificationsEnabled(): Flow<Boolean> {
        return dataStore.notificationsEnabled()
    }

    private fun getUniqueArticles(
        newArticles: List<Article>,
        oldArticles: List<Article>
    ): List<Article> {
        val difference = newArticles.toSet().minus(oldArticles.toSet())
        return difference.toList()
    }
}