package com.example.headlinehunter.core.domain

import kotlinx.coroutines.flow.Flow

interface DataStorePreferences {

    suspend fun updateNotifications(notificationsEnabled: Boolean)
    suspend fun setTheme(isDarkMode: Boolean)
    fun getTheme() : Flow<Boolean>
    fun notificationsEnabled(): Flow<Boolean>
}