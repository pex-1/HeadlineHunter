package com.example.headlinehunter.ui.settings

sealed interface SettingsAction {
    data object OnBackClick : SettingsAction
    data object OnEnableNotifications : SettingsAction
    data object OnEnableDarkMode : SettingsAction
    data object OnShowGoToSettingsDialog : SettingsAction
}