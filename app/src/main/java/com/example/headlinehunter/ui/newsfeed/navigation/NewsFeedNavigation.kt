package com.example.headlinehunter.ui.newsfeed.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.headlinehunter.ui.newsfeed.NewsFeedScreenRoot
import kotlinx.serialization.Serializable

@Serializable
data object NewsFeedRoute

fun NavController.navigateToNewsFeed() = navigate(route = NewsFeedRoute)

fun NavGraphBuilder.newsFeedScreen(
    onBackClick: () -> Unit
) {
    composable<NewsFeedRoute> {
        NewsFeedScreenRoot(onBackClick)
    }
}