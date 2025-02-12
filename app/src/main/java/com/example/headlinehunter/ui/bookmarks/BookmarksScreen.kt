package com.example.headlinehunter.ui.bookmarks

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
fun BookmarksScreenRoot(
    onArticleClick: (Int) -> Unit,
    onAddRssFeedClick: () -> Unit,
    onBackClick: () -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope,
    sharedTransitionScope: SharedTransitionScope,
    viewModel: BookmarksViewModel = koinViewModel()
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    BookmarksScreen(
        state = state,
        animatedVisibilityScope = animatedVisibilityScope,
        sharedTransitionScope = sharedTransitionScope,
        onAction = { action ->
            when (action) {
                is BookmarksAction.OnBackClick -> onBackClick()
                is BookmarksAction.OnArticleClick -> onArticleClick(action.articleId)
                else -> viewModel.onAction(action)
            }
        },
        onAddRssFeedClick = onAddRssFeedClick
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun BookmarksScreen(
    state: BookmarksState,
    animatedVisibilityScope: AnimatedVisibilityScope,
    sharedTransitionScope: SharedTransitionScope,
    onAction: (BookmarksAction) -> Unit,
    onAddRssFeedClick: () -> Unit
) {

    Column {
        HeadlineHunterToolbar(
            title = stringResource(R.string.headline_hunter),
            onMenuItemClick = {
                if (it == MenuAction.ADD_RSS_FEEDS) {
                    onAddRssFeedClick()
                }
            })

        if (state.favoriteArticles.isEmpty()) {
            EmptyState()
        }
        LazyColumn(
            state = rememberLazyListState(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                items = state.favoriteArticles,
                key = {
                    it.id
                }
            ) { article ->
                ArticleListItem(
                    modifier = Modifier.animateItem(),
                    animatedVisibilityScope = animatedVisibilityScope,
                    sharedTransitionScope = sharedTransitionScope,
                    title = article.title,
                    isArticleRead = article.isArticleRead,
                    articleId = article.id,
                    description = article.description,
                    imageUrl = article.image,
                    date = article.pubDate.getDateFromMills(),
                    isFavorite = article.isFavorite,
                    onArticleClick = {
                        onAction(BookmarksAction.OnArticleClick(article.id))
                    }
                ) {
                    onAction(BookmarksAction.OnRemoveFromFavoritesClick(article))
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
                BookmarksScreen(BookmarksState(), animatedVisibilityScope = this,
                    sharedTransitionScope = this@SharedTransitionLayout, {}) { }
            }
        }
    }
}