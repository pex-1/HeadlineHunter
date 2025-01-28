package com.example.headlinehunter.core.presentation.components

import android.content.Context
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.headlinehunter.R
import com.example.headlinehunter.core.presentation.util.Constants

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.ArticleImageCard(
    modifier: Modifier = Modifier,
    articleId: Int,
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
        contentDescription = stringResource(R.string.article_image),
        modifier = modifier
            .sharedElement(
                state = rememberSharedContentState(key = "$articleId/image"),
                animatedVisibilityScope = animatedVisibilityScope,
                boundsTransform = { _, _ ->
                    tween(durationMillis = Constants.DETAILS_IMAGE_DELAY)
                }
            )
            .clip(RoundedCornerShape(6.dp))
            .height(100.dp)
            .width(110.dp),

        contentScale = ContentScale.Crop,
        loading = { LoadingState() },
    )
}