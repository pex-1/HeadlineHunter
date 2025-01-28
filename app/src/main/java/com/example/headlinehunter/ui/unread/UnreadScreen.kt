package com.example.headlinehunter.ui.unread

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.headlinehunter.R
import com.example.headlinehunter.core.domain.util.getDateFromMills
import com.example.headlinehunter.core.presentation.components.ArticleListItem
import com.example.headlinehunter.core.presentation.components.EmptyState
import com.example.headlinehunter.core.presentation.components.HeadlineHunterToolbar
import com.example.headlinehunter.core.presentation.components.MenuAction
import com.example.headlinehunter.ui.theme.HeadlineHunterTheme
import com.example.headlinehunter.util.DevicePreviews
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun UnreadScreenRoot(
    onArticleClick: (Int) -> Unit,
    onBackClick: () -> Unit,
    onAddRssFeedClick: () -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope,
    sharedTransitionScope: SharedTransitionScope,
    viewModel: UnreadViewModel = koinViewModel()
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    UnreadScreen(
        state = state,
        animatedVisibilityScope = animatedVisibilityScope,
        sharedTransitionScope = sharedTransitionScope,
        onAction = { action ->
            when (action) {
                is UnreadAction.OnBackClick -> {
                    onBackClick()
                }

                is UnreadAction.OnArticleClick -> onArticleClick(action.articleId)
                is UnreadAction.OnAddRssFeedClick -> onAddRssFeedClick()
                else -> viewModel.onAction(action)
            }
        }
    )
}

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
fun UnreadScreen(
    state: UnreadState,
    animatedVisibilityScope: AnimatedVisibilityScope,
    sharedTransitionScope: SharedTransitionScope,
    onAction: (UnreadAction) -> Unit,
) {

    Column {
        HeadlineHunterToolbar(
            title = stringResource(R.string.headlinehunter),
            onMenuItemClick = {
                if (it == MenuAction.ADD_RSS_FEEDS) {
                    onAction(UnreadAction.OnAddRssFeedClick)
                }
            })

        if (state.articles.isEmpty()) {
            EmptyState()
        }
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
                        onAction(UnreadAction.OnArticleClick(article.id))
                    }
                ) {
                    onAction(UnreadAction.OnFavoritesClick(article))
                }
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@DevicePreviews
@Composable
fun BookmarksScreenPreview() {
    HeadlineHunterTheme {
        SharedTransitionLayout {
            AnimatedVisibility(visible = true) {
                UnreadScreen(
                    UnreadState(),
                    this, this@SharedTransitionLayout
                ) { }
            }
        }
    }
}