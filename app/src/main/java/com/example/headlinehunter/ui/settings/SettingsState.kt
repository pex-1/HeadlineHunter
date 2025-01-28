package com.example.headlinehunter.ui.settings

data class SettingsState(
    val isLoading: Boolean = false,
    val notificationsEnabled: Boolean = false,
    val showNotificationSettingsDialog: Boolean = false,
    val darkModeEnabled: Boolean = false
)