package com.example.headlinehunter.ui.bookmarks.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.headlinehunter.ui.bookmarks.BookmarksScreenRoot
import kotlinx.serialization.Serializable

@Serializable
data object BookmarksRoute

fun NavController.navigateToBookmarks(navOptions: NavOptions) =
    navigate(route = BookmarksRoute, navOptions)

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.bookmarksScreen(
    onArticleClick: (Int) -> Unit,
    onBackClick: () -> Unit,
    onAddRssFeedClick: () -> Unit,
    sharedTransitionScope: SharedTransitionScope
) {
    composable<BookmarksRoute> {
        BookmarksScreenRoot(
            onArticleClick = onArticleClick,
            onBackClick = onBackClick,
            onAddRssFeedClick = onAddRssFeedClick,
            animatedVisibilityScope = this,
            sharedTransitionScope = sharedTransitionScope
        )
    }
}