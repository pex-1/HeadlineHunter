package com.example.headlinehunter.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.headlinehunter.database.util.DatabaseConstants

@Entity(tableName = DatabaseConstants.CHANNEL_TABLE)
data class ChannelEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val description: String,
    val link: String,
    val image: String,
    val lastUpdatedDate: String,
    val isSelected: Boolean,
    val notificationsEnabled: Boolean
)