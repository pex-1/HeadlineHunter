package com.example.headlinehunter.core.nav

import androidx.compose.ui.graphics.vector.ImageVector
import com.example.headlinehunter.core.presentation.util.BookmarkIcon
import com.example.headlinehunter.core.presentation.util.HomeIcon
import com.example.headlinehunter.core.presentation.util.SettingsIcon
import com.example.headlinehunter.core.presentation.util.UnreadIcon
import com.example.headlinehunter.ui.bookmarks.navigation.BookmarksRoute
import com.example.headlinehunter.ui.home.navigation.HomeRoute
import com.example.headlinehunter.ui.settings.navigation.SettingsRoute
import com.example.headlinehunter.ui.unread.navigation.UnreadRoute
import kotlin.reflect.KClass

enum class TopLevelDestination(
    val title: String,
    val icon: ImageVector,
    val route: KClass<*>
) {

    HOME(
        title = "Home",
        icon = HomeIcon,
        route = HomeRoute::class
    ),

    UNREAD(
        title = "Unread",
        icon = UnreadIcon,
        route = UnreadRoute::class
    ),

    BOOKMARKS(
        title = "Bookmarks",
        icon = BookmarkIcon,
        route = BookmarksRoute::class
    ),

    SETTINGS(
        title = "Settings",
        icon = SettingsIcon,
        route = SettingsRoute::class
    )

}