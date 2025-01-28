package com.example.headlinehunter.database

import android.database.sqlite.SQLiteFullException
import androidx.room.withTransaction
import com.example.headlinehunter.core.domain.channel.Channel
import com.example.headlinehunter.core.domain.channel.ChannelId
import com.example.headlinehunter.core.domain.channel.LocalChannelDataSource
import com.example.headlinehunter.core.domain.util.DataError
import com.example.headlinehunter.core.domain.util.Result
import com.example.headlinehunter.database.dao.ChannelDao
import com.example.headlinehunter.database.mappers.toChannel
import com.example.headlinehunter.database.mappers.toChannelEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomLocalChannelDataSource(
    private val channelDao: ChannelDao,
    private val rssDatabase: RssDatabase
) : LocalChannelDataSource {

    override fun getChannels(): Flow<List<Channel>> {
        return channelDao.getChannels()
            .map { channels ->
                channels.map {
                    it.toChannel()
                }
            }
    }

    override suspend fun getSubscribedChannels(): List<Channel> {
        return channelDao.getSubscribedChannels().map { it.toChannel() }
    }

    override suspend fun getChannelById(channelId: Int): Channel? {
        return channelDao.getChannelById(channelId)?.toChannel()
    }

    override suspend fun addChannel(channel: Channel): Result<ChannelId, DataError.Local> {
        return try {
            val entity = channel.toChannelEntity()
            channelDao.addChannel(entity)
            Result.Success(entity.id)
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun removeChannel(id: Int) {
        rssDatabase.withTransaction {
            rssDatabase.articleDao.removeArticles(id)
            channelDao.removeChannel(id)
        }
    }

    override suspend fun channelExists(channelLink: String): Boolean {
        val channel = channelDao.channelExists(channelLink)
        return channel != null
    }

    override suspend fun updateChannel(channel: Channel) {
        channelDao.updateChannel(channel.toChannelEntity())
    }
}