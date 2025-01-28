package com.example.headlinehunter.core.domain.channel

import com.example.headlinehunter.core.domain.util.DataError
import com.example.headlinehunter.core.domain.util.Result
import kotlinx.coroutines.flow.Flow

typealias ChannelId = Int

interface LocalChannelDataSource {

    fun getChannels(): Flow<List<Channel>>
    suspend fun getSubscribedChannels(): List<Channel>
    suspend fun getChannelById(channelId: Int): Channel?
    suspend fun addChannel(channel: Channel): Result<ChannelId, DataError.Local>
    suspend fun removeChannel(id: Int)
    suspend fun channelExists(channelLink: String): Boolean
    suspend fun updateChannel(channel: Channel)

}