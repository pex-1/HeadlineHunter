package com.example.headlinehunter.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.headlinehunter.database.util.DatabaseConstants

@Entity(
    tableName = DatabaseConstants.ARTICLE_TABLE
)
data class ArticleEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val author: String,
    val description: String,
    val channelId: Int,
    val link: String,
    val image: String,
    val pubDate: Long,
    val isFavorite: Boolean,
    val isArticleRead: Boolean
)