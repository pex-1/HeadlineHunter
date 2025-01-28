package com.example.headlinehunter.core.nav

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.headlinehunter.ui.bookmarks.navigation.bookmarksScreen
import com.example.headlinehunter.ui.details.navigation.detailsScreen
import com.example.headlinehunter.ui.details.navigation.navigateToDetails
import com.example.headlinehunter.ui.home.navigation.HomeRoute
import com.example.headlinehunter.ui.home.navigation.homeScreen
import com.example.headlinehunter.ui.newsfeed.navigation.navigateToNewsFeed
import com.example.headlinehunter.ui.newsfeed.navigation.newsFeedScreen
import com.example.headlinehunter.ui.settings.navigation.settingsScreen
import com.example.headlinehunter.ui.unread.navigation.unreadScreen

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun NavigationRoot(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {

    SharedTransitionLayout {
        NavHost(
            navController = navController,
            startDestination = HomeRoute,
            modifier = modifier
        ) {
            homeScreen(
                onArticleClick = { articleId ->
                    navController.navigateToDetails(articleId)
                },
                onBackClick = navController::popBackStack,
                onAddRssFeedClick = {
                    navController.navigateToNewsFeed()
                },
                sharedTransitionScope = this@SharedTransitionLayout
            )

            unreadScreen(
                onArticleClick = { articleId ->
                    navController.navigateToDetails(articleId)
                },
                onBackClick = navController::navigateUp,
                onAddRssFeedClick = {
                    navController.navigateToNewsFeed()
                },
                sharedTransitionScope = this@SharedTransitionLayout
            )

            bookmarksScreen(
                onArticleClick = { articleId ->
                    navController.navigateToDetails(articleId)
                },
                onBackClick = navController::navigateUp,
                onAddRssFeedClick = {
                    navController.navigateToNewsFeed()
                },
                sharedTransitionScope = this@SharedTransitionLayout
            )

            settingsScreen(
                onBackClick = navController::navigateUp,
            )

            newsFeedScreen(
                onBackClick = navController::navigateUp
            )

            detailsScreen(
                onBackClick = navController::navigateUp,
                sharedTransitionScope = this@SharedTransitionLayout
            )

        }
    }

}