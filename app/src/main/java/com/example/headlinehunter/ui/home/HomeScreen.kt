package com.example.headlinehunter.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.headlinehunter.R
import com.example.headlinehunter.core.domain.util.getDateFromMills
import com.example.headlinehunter.core.presentation.components.ArticleListItem
import com.example.headlinehunter.core.presentation.components.EmptyState
import com.example.headlinehunter.core.presentation.components.HeadlineHunterToolbar
import com.example.headlinehunter.core.presentation.components.LoadingState
import com.example.headlinehunter.core.presentation.components.MenuAction
import com.example.headlinehunter.core.presentation.components.MenuItem
import com.example.headlinehunter.core.presentation.util.RssIcon
import com.example.headlinehunter.ui.home.components.ChannelSelect
import com.example.headlinehunter.ui.theme.HeadlineHunterTheme
import com.example.headlinehunter.util.DevicePreviews
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun HomeScreenRoot(
    onArticleClick: (Int) -> Unit,
    onAddRssFeedClick: () -> Unit,
    onBackClick: () -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope,
    sharedTransitionScope: SharedTransitionScope,
    viewModel: HomeViewModel = koinViewModel()
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    HomeScreen(
        state = state,
        animatedVisibilityScope = animatedVisibilityScope,
        sharedTransitionScope = sharedTransitionScope,
        onAction = { action ->
            when (action) {
                is HomeAction.OnBackClick -> onBackClick()
                is HomeAction.OnArticleClick -> onArticleClick(action.articleId)
                is HomeAction.OnAddRssFeedClick -> onAddRssFeedClick()
                else -> {}
            }
            viewModel.onAction(action)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun HomeScreen(
    state: HomeState,
    animatedVisibilityScope: AnimatedVisibilityScope,
    sharedTransitionScope: SharedTransitionScope,
    onAction: (HomeAction) -> Unit
) {

    if (state.isLoadingData) {
        Box(modifier = Modifier.fillMaxSize()) {
            LoadingState(modifier = Modifier.align(Alignment.Center))
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {

            val menuItems = listOf(
                MenuItem(
                    ImageVector.vectorResource(if(state.collapseFeedSelection) R.drawable.expand else R.drawable.collapse),
                    MenuAction.COLLAPSE_FEED_SELECTION
                ),
                MenuItem(RssIcon, MenuAction.ADD_RSS_FEEDS)
            )
            HeadlineHunterToolbar(
                title = stringResource(R.string.headline_hunter),
                menuItems = menuItems,
                onMenuItemClick = { action ->
                    when (action) {
                        MenuAction.ADD_RSS_FEEDS -> {
                            onAction(HomeAction.OnAddRssFeedClick)
                        }

                        MenuAction.COLLAPSE_FEED_SELECTION -> {
                            onAction(HomeAction.OnCollapseChannelsClick)
                        }

                        else -> {}
                    }
                })
            if (state.channels.isEmpty() && state.isRefreshing.not()) {
                EmptyState()
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize()
                    .then(
                        if (state.collapseFeedSelection) {
                            Modifier.height(0.dp)
                        } else {
                            Modifier.wrapContentHeight()
                        }
                    )
            ) {
                LazyRow(
                    modifier = Modifier.padding(vertical = 16.dp),
                    state = rememberLazyListState(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(
                        items = state.channels,
                        key = {
                            it.id
                        }
                    ) { channel ->
                        ChannelSelect(
                            channel = channel,
                            onChannelClick = {
                                onAction(HomeAction.OnChannelClick(it))
                            }
                        )
                    }
                }

                HorizontalDivider(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .alpha(if (state.channels.isEmpty()) 0f else 1f),
                    thickness = 1.dp, color = MaterialTheme.colorScheme.surfaceVariant
                )
            }

            PullToRefreshBox(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth(),
                state = rememberPullToRefreshState(),
                isRefreshing = state.isRefreshing,
                onRefresh = {
                    onAction(HomeAction.OnPullToRefresh)
                }
            ) {
                LazyColumn(
                    state = rememberLazyListState(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(
                        items = state.articles,
                        key = {
                            it.id
                        }
                    ) { article ->
                        ArticleListItem(
                            modifier = Modifier
                                .animateItem(),
                            isArticleRead = article.isArticleRead,
                            animatedVisibilityScope = animatedVisibilityScope,
                            sharedTransitionScope = sharedTransitionScope,
                            title = article.title,
                            articleId = article.id,
                            description = article.description,
                            imageUrl = article.image,
                            date = article.pubDate.getDateFromMills(),
                            isFavorite = article.isFavorite,
                            onArticleClick = {
                                onAction(HomeAction.OnArticleClick(article.id))
                            }
                        ) {
                            onAction(HomeAction.OnAddToFavoritesClick(article))
                        }
                    }
                }
            }
        }
    }

}

@OptIn(ExperimentalSharedTransitionApi::class)
@DevicePreviews
@Composable
fun HomeScreenPreview() {
    HeadlineHunterTheme {
        SharedTransitionLayout {
            AnimatedVisibility(visible = true) {
                HomeScreen(HomeState(), animatedVisibilityScope = this,
                    sharedTransitionScope = this@SharedTransitionLayout, {})
            }
        }
    }
}