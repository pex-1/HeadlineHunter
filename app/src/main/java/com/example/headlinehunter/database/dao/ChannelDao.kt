package com.example.headlinehunter.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.headlinehunter.database.entity.ChannelEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ChannelDao {

    @Query("SELECT * FROM channel_table")
    fun getChannels(): Flow<List<ChannelEntity>>

    @Query("SELECT * FROM channel_table WHERE notificationsEnabled = 1")
    suspend fun getSubscribedChannels(): List<ChannelEntity>

    @Query("SELECT * FROM channel_table WHERE id = :channelId")
    suspend fun getChannelById(channelId: Int): ChannelEntity?

    @Insert
    suspend fun addChannel(channel: ChannelEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateChannel(channel: ChannelEntity)

    @Query("DELETE FROM channel_table WHERE id = :channelId")
    suspend fun removeChannel(channelId: Int)

    @Query("SELECT * FROM channel_table WHERE link = :channelLink")
    suspend fun channelExists(channelLink: String): ChannelEntity?

}