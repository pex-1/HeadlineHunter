package com.example.headlinehunter.ui.details.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.headlinehunter.ui.details.DetailsScreenRoot
import kotlinx.serialization.Serializable

@Serializable
data class DetailsRoute(val articleId: Int)

fun NavController.navigateToDetails(articleId: Int) = navigate(route = DetailsRoute(articleId))

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.detailsScreen(
    onBackClick: () -> Unit,
    sharedTransitionScope: SharedTransitionScope
) {
    composable<DetailsRoute> {
        val args = it.toRoute<DetailsRoute>()
        DetailsScreenRoot(
            articleId = args.articleId,
            onBackClick = onBackClick,
            animatedVisibilityScope = this,
            sharedTransitionScope = sharedTransitionScope
        )
    }
}