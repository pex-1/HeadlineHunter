package com.example.headlinehunter.ui.unread.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.headlinehunter.ui.unread.UnreadScreenRoot
import kotlinx.serialization.Serializable

@Serializable
data object UnreadRoute

fun NavController.navigateToUnread(navOptions: NavOptions) =
    navigate(route = UnreadRoute, navOptions)

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.unreadScreen(
    onArticleClick: (Int) -> Unit,
    onBackClick: () -> Unit,
    onAddRssFeedClick: () -> Unit,
    sharedTransitionScope: SharedTransitionScope
) {
    composable<UnreadRoute> {
        UnreadScreenRoot(
            onArticleClick = onArticleClick,
            onBackClick = onBackClick,
            onAddRssFeedClick = onAddRssFeedClick,
            animatedVisibilityScope = this,
            sharedTransitionScope = sharedTransitionScope
        )
    }
}