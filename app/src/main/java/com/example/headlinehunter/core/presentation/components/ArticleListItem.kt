package com.example.headlinehunter.core.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.headlinehunter.core.presentation.util.BookmarkIcon
import com.example.headlinehunter.core.presentation.util.BookmarkOutlineIcon
import com.example.headlinehunter.core.presentation.util.Constants
import com.example.headlinehunter.ui.theme.HeadlineHunterTheme

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ArticleListItem(
    modifier: Modifier = Modifier,
    animatedVisibilityScope: AnimatedVisibilityScope,
    sharedTransitionScope: SharedTransitionScope,
    title: String = "",
    isArticleRead: Boolean = false,
    description: String = "",
    articleId: Int = 0,
    imageUrl: String = "",
    date: String = "",
    isFavorite: Boolean = false,
    onArticleClick: () -> Unit = {},
    onAddToFavoritesClick: () -> Unit = {},
) {

    with(sharedTransitionScope) {
        Row(
            modifier = modifier
                .alpha(if (isArticleRead) 0.5f else 1f)
                .clickable(onClick = {
                    onArticleClick()
                })
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .padding(vertical = 16.dp, horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            val context = LocalContext.current
            ArticleImageCard(
                modifier,
                articleId,
                context,
                imageUrl,
                animatedVisibilityScope
            )

            val fontWeight = if (isArticleRead) FontWeight.ExtraLight else FontWeight.Medium
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier
                        .height(IntrinsicSize.Max)
                ) {
                    Box {
                        VerticalDivider(
                            modifier = Modifier.sharedElement(
                                state = rememberSharedContentState(key = articleId),
                                animatedVisibilityScope = animatedVisibilityScope,
                                boundsTransform = { _, _ ->
                                    tween(durationMillis = Constants.DETAILS_IMAGE_DELAY)
                                }
                            ),
                            thickness = 2.dp,
                            color = MaterialTheme.colorScheme.primary)
                        Column {
                            Text(
                                modifier = Modifier
                                    .padding(start = 8.dp, bottom = 4.dp)
                                    .sharedElement(
                                        state = rememberSharedContentState(key = "$articleId/title"),
                                        animatedVisibilityScope = animatedVisibilityScope,
                                        boundsTransform = { _, _ ->
                                            tween(durationMillis = Constants.DETAILS_IMAGE_DELAY)
                                        }
                                    ),
                                style = MaterialTheme.typography.bodyLarge,
                                text = title,
                                fontWeight = fontWeight,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                            Text(
                                modifier = Modifier
                                    .padding(start = 8.dp)
                                    .sharedElement(
                                        state = rememberSharedContentState(key = "$articleId/description"),
                                        animatedVisibilityScope = animatedVisibilityScope,
                                        boundsTransform = { _, _ ->
                                            tween(durationMillis = Constants.DETAILS_IMAGE_DELAY)
                                        }
                                    ),
                                text = description,
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = fontWeight,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        }

                    }

                }

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    modifier = Modifier.padding(start = 8.dp, top = 8.dp),
                    text = date,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = fontWeight
                )
            }

            IconButton(
                onClick = {
                    onAddToFavoritesClick()
                }
            ) {
                Icon(
                    imageVector = if (isFavorite) BookmarkIcon
                    else BookmarkOutlineIcon,
                    contentDescription = null,
                    tint = if (isFavorite) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.surfaceVariant
                )
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview
@Composable
private fun ArticleListItemPreview() {
    HeadlineHunterTheme {
        SharedTransitionLayout {
            AnimatedVisibility(visible = true) {
                ArticleListItem(
                    title = "Lorem Ipsum is simply dummy text of the printing.",
                    description = "Should be something very long so it would stretch over three lines or more",
                    date = "Jun 20, 2022",
                    animatedVisibilityScope = this,
                    sharedTransitionScope = this@SharedTransitionLayout
                )

            }
        }
    }
}