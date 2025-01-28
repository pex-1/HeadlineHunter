package com.example.headlinehunter.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.headlinehunter.R
import com.example.headlinehunter.core.presentation.components.LoadingState

private val defaultModifier = Modifier
    .fillMaxWidth()
    .aspectRatio(1f)
    .clip(CircleShape)

@Composable
fun ChannelImage(
    imageUrl: String,
    modifier: Modifier,
    shape: Shape = RectangleShape,
    selected: Boolean = true
) {

    val context = LocalContext.current

    SubcomposeAsyncImage(
        model = ImageRequest.Builder(context)
            .data(imageUrl)
            .error(R.drawable.image_empty_state)
            .crossfade(400)
            .build(),
        contentDescription = stringResource(R.string.channel_image),
        modifier = modifier
            .clip(shape)
            .border(
                if (selected) 2.dp else 0.dp,
                if (selected)
                    MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                shape = CircleShape
            ),

        loading = { LoadingState() },
    )
}

@Preview
@Composable
private fun CharacterImagePreview() {
    ChannelImage(
        imageUrl = "",
        modifier = defaultModifier.then(
            Modifier.background(
                brush = Brush.verticalGradient(listOf(Color.White, Color.Black))
            )
        )
    )
}