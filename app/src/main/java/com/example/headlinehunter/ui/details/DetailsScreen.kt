package com.example.headlinehunter.ui.details

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.headlinehunter.core.presentation.components.HeadlineHunterToolbar
import com.example.headlinehunter.core.presentation.util.ArrowForwardIcon
import com.example.headlinehunter.core.presentation.util.Constants
import com.example.headlinehunter.ui.details.components.DetailsCoverImage
import com.example.headlinehunter.ui.theme.HeadlineHunterTheme
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun DetailsScreenRoot(
    articleId: Int,
    onBackClick: () -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope,
    sharedTransitionScope: SharedTransitionScope,
    viewModel: DetailsViewModel = koinViewModel()
) {

    viewModel.getArticleById(articleId)
    val state by viewModel.state.collectAsStateWithLifecycle()
    DetailsScreen(
        state = state,
        animatedVisibilityScope = animatedVisibilityScope,
        sharedTransitionScope = sharedTransitionScope,
        onAction = { action ->
            if (action == DetailsAction.OnBackClick) {
                onBackClick()
            } else viewModel.onAction(action)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun DetailsScreen(
    state: DetailsState,
    animatedVisibilityScope: AnimatedVisibilityScope,
    sharedTransitionScope: SharedTransitionScope,
    onAction: (DetailsAction) -> Unit,
) {
    with(sharedTransitionScope) {
        val context = LocalContext.current

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            Box {
                DetailsCoverImage(
                    articleId = state.article.id,
                    modifier = Modifier,
                    context = context,
                    imageUrl = state.article.image,
                    animatedVisibilityScope = animatedVisibilityScope
                )

                HeadlineHunterToolbar(showBackButton = true, menuItems = listOf(), onBackClick = {
                    onAction(DetailsAction.OnBackClick)
                })
            }

            Box(
                modifier = Modifier
                    .sharedElement(
                        state = rememberSharedContentState(key = "${state.article.id}/container"),
                        animatedVisibilityScope = animatedVisibilityScope,
                        boundsTransform = { _, _ ->
                            tween(durationMillis = Constants.DETAILS_IMAGE_DELAY)
                        }
                    )
                    .fillMaxSize()
                    .offset(y = (-10).dp)
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                Column(Modifier.padding(16.dp)) {

                    Row(
                        modifier = Modifier
                            .height(IntrinsicSize.Min)
                    ) {

                        VerticalDivider(
                            modifier = Modifier.sharedElement(
                                state = rememberSharedContentState(key = state.article.id),
                                animatedVisibilityScope = animatedVisibilityScope,
                                boundsTransform = { _, _ ->
                                    tween(durationMillis = Constants.DETAILS_IMAGE_DELAY)
                                }
                            ),
                            thickness = 3.dp, color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .sharedElement(
                                    state = rememberSharedContentState(key = "${state.article.id}/title"),
                                    animatedVisibilityScope = animatedVisibilityScope,
                                    boundsTransform = { _, _ ->
                                        tween(durationMillis = Constants.DETAILS_IMAGE_DELAY)
                                    }
                                ),
                            text = state.article.title,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }

                    Text(
                        modifier = Modifier
                            .padding(vertical = 24.dp)
                            .sharedElement(
                                state = rememberSharedContentState(key = "${state.article.id}/description"),
                                animatedVisibilityScope = animatedVisibilityScope,
                                boundsTransform = { _, _ ->
                                    tween(durationMillis = Constants.DETAILS_IMAGE_DELAY)
                                }
                            ),
                        text = state.article.description,
                        style = MaterialTheme.typography.bodyMedium
                    )

                    TextButton(
                        onClick = {
                            val intent =
                                Intent(Intent.ACTION_VIEW, Uri.parse(state.article.link))
                            context.startActivity(intent)
                        },
                        contentPadding = PaddingValues()
                    ) {
                        Text(
                            text = "Read full article",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.tertiary,
                            fontWeight = FontWeight.Medium
                        )
                        Icon(
                            imageVector = ArrowForwardIcon,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.tertiary
                        )
                    }

                }
            }
        }
    }

}

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview
@Composable
private fun DetailsScreenPreview() {
    HeadlineHunterTheme {
        SharedTransitionLayout {
            AnimatedVisibility(visible = true) {
                DetailsScreen(
                    state = DetailsState(),
                    animatedVisibilityScope = this,
                    sharedTransitionScope = this@SharedTransitionLayout
                ) {}
            }
        }
    }
}