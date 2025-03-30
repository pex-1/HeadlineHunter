package com.example.headlinehunter.core.domain

import kotlinx.coroutines.flow.Flow

interface DataStorePreferences {

    suspend fun updateNotifications(notificationsEnabled: Boolean)
    suspend fun setTheme(isDarkMode: Boolean)
    suspend fun setCollapseChannels(collapse: Boolean)
    fun getCollapseChannels() : Flow<Boolean>
    fun getTheme() : Flow<Boolean>
    fun notificationsEnabled(): Flow<Boolean>
}