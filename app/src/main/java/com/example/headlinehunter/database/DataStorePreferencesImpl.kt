package com.example.headlinehunter.database

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.headlinehunter.core.domain.DataStorePreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class DataStorePreferencesImpl(
    private val context: Context
) : DataStorePreferences {

    companion object {
        val notificationsKey = booleanPreferencesKey("notifications_key")
        val darkModeKey = booleanPreferencesKey("dark_mode_key")
        val collapseChannelsKey = booleanPreferencesKey("collapse_channels_key")
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("userInfo")

    override suspend fun updateNotifications(notificationsEnabled: Boolean) {
        context.dataStore.edit { pref ->
            pref[notificationsKey] = notificationsEnabled
        }
    }

    override suspend fun setTheme(isDarkMode: Boolean) {
        context.dataStore.edit { pref ->
            pref[darkModeKey] = isDarkMode
        }
    }

    override suspend fun setCollapseChannels(collapse: Boolean) {
        context.dataStore.edit { pref ->
            pref[collapseChannelsKey] = collapse
        }
    }

    override fun getCollapseChannels(): Flow<Boolean> {
        return context.dataStore.data
            .catchAndHandleError()
            .map { pref ->
                pref[collapseChannelsKey] ?: false
            }
    }

    override fun notificationsEnabled(): Flow<Boolean> {
        return context.dataStore.data
            .catchAndHandleError()
            .map { pref ->
                pref[notificationsKey] ?: false
            }
    }

    override fun getTheme(): Flow<Boolean> {
        return context.dataStore.data
            .catchAndHandleError()
            .map { pref ->
                pref[darkModeKey] ?: false
            }
    }

    private fun Flow<Preferences>.catchAndHandleError(): Flow<Preferences> {
        this.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        return this@catchAndHandleError
    }
}

