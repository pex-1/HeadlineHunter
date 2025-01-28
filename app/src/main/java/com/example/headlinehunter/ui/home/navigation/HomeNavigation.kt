package com.example.headlinehunter.ui.home.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.headlinehunter.ui.home.HomeScreenRoot
import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute

fun NavController.navigateToHome(navOptions: NavOptions) = navigate(route = HomeRoute, navOptions)

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.homeScreen(
    onArticleClick: (Int) -> Unit,
    onBackClick: () -> Unit,
    onAddRssFeedClick: () -> Unit,
    sharedTransitionScope: SharedTransitionScope
) {
    composable<HomeRoute> {
        HomeScreenRoot(
            onArticleClick = onArticleClick,
            onBackClick = onBackClick,
            onAddRssFeedClick = onAddRssFeedClick,
            animatedVisibilityScope = this,
            sharedTransitionScope = sharedTransitionScope
        )
    }
}