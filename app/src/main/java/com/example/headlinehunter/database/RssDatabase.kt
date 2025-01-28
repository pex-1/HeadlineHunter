package com.example.headlinehunter.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.headlinehunter.database.dao.ArticleDao
import com.example.headlinehunter.database.dao.ChannelDao
import com.example.headlinehunter.database.entity.ArticleEntity
import com.example.headlinehunter.database.entity.ChannelEntity

@Database(
    entities = [
        ChannelEntity::class,
        ArticleEntity::class
    ],
    version = 1
)
abstract class RssDatabase : RoomDatabase() {

    abstract val channelDao: ChannelDao
    abstract val articleDao: ArticleDao

}