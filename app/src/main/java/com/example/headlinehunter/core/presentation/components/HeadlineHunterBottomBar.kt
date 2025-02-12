package com.example.headlinehunter.core.presentation.components

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navOptions
import com.example.headlinehunter.core.nav.NavigationRoot
import com.example.headlinehunter.core.nav.TopLevelDestination
import com.example.headlinehunter.ui.bookmarks.navigation.navigateToBookmarks
import com.example.headlinehunter.ui.home.navigation.navigateToHome
import com.example.headlinehunter.ui.settings.navigation.navigateToSettings
import com.example.headlinehunter.ui.unread.navigation.navigateToUnread
import kotlin.reflect.KClass

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeadlineHunterBottomBar(
    navController: NavHostController
) {
    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.entries

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val showBars = topLevelDestinations.any {
        currentDestination.isRouteInHierarchy(it.route)
    }

    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = showBars,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.graphicsLayer {
                        clip = true
                        shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
                        shadowElevation = 80f
                    }
                ) {
                    topLevelDestinations.forEach { destination ->

                        val isSelected = currentDestination.isRouteInHierarchy(destination.route)

                        NavigationBarItem(
                            selected = isSelected,
                            onClick = {
                                navigateToTopLevelDestination(destination, navController)
                            },
                            icon = {
                                Icon(
                                    imageVector = destination.icon,
                                    contentDescription = destination.title
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.primary,
                                unselectedIconColor = MaterialTheme.colorScheme.tertiary,
                                indicatorColor = Color.Transparent
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavigationRoot(
            navController,
            if (showBars) Modifier.padding(bottom = innerPadding.calculateBottomPadding())
            else Modifier.navigationBarsPadding()
        )
    }
}

@SuppressLint("RestrictedApi")
private fun NavDestination?.isRouteInHierarchy(route: KClass<*>) =
    this?.hierarchy?.any { it.hasRoute(route = route) } == true

fun navigateToTopLevelDestination(
    topLevelDestination: TopLevelDestination,
    navController: NavController
) {
    val topLevelNavOptions = navOptions {
        popUpTo(navController.graph.startDestinationId) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }

    when (topLevelDestination) {
        TopLevelDestination.HOME -> navController.navigateToHome(topLevelNavOptions)
        TopLevelDestination.UNREAD -> navController.navigateToUnread(topLevelNavOptions)
        TopLevelDestination.BOOKMARKS -> navController.navigateToBookmarks(topLevelNavOptions)
        TopLevelDestination.SETTINGS -> navController.navigateToSettings(topLevelNavOptions)
    }
}