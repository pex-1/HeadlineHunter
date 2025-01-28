package com.example.headlinehunter.ui.details.components

import android.content.Context
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.headlinehunter.R
import com.example.headlinehunter.core.presentation.components.LoadingState
import com.example.headlinehunter.core.presentation.util.Constants

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.DetailsCoverImage(
    articleId: Int,
    modifier: Modifier,
    context: Context,
    imageUrl: String,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    SubcomposeAsyncImage(
        model = ImageRequest.Builder(context)
            .data(imageUrl)
            .error(R.drawable.image_empty_state)
            .crossfade(400)
            .build(),
        contentDescription = "Article image",
        modifier = modifier
            .sharedElement(
                state = rememberSharedContentState(key = "$articleId/image"),
                animatedVisibilityScope = animatedVisibilityScope,
                boundsTransform = { _, _ ->
                    tween(durationMillis = Constants.DETAILS_IMAGE_DELAY)
                }
            )
            .height(300.dp)
            .fillMaxWidth(),
        contentScale = ContentScale.Crop,
        loading = { LoadingState() }
    )
}