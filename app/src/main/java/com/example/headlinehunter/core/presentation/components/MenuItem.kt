package com.example.headlinehunter.core.presentation.components

import androidx.compose.ui.graphics.vector.ImageVector

data class MenuItem(
    val icon: ImageVector,
    val action: MenuAction
)

enum class MenuAction {
    ADD_RSS_FEEDS,
    COLLAPSE_FEED_SELECTION,
    OPEN_ADD_RSS_DIALOG
}
