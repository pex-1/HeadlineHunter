package com.example.headlinehunter.ui.settings.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.headlinehunter.ui.settings.SettingsScreenRoot
import kotlinx.serialization.Serializable

@Serializable
data object SettingsRoute

fun NavController.navigateToSettings(navOptions: NavOptions) =
    navigate(route = SettingsRoute, navOptions)

fun NavGraphBuilder.settingsScreen(
    onBackClick: () -> Unit,
) {
    composable<SettingsRoute> {
        SettingsScreenRoot(onBackClick)
    }
}