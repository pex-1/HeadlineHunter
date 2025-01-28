package com.example.headlinehunter.ui.newsfeed.components

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.headlinehunter.R
import com.example.headlinehunter.core.domain.channel.Channel
import com.example.headlinehunter.core.domain.util.DataError
import com.example.headlinehunter.core.presentation.components.LoadingState
import com.example.headlinehunter.core.presentation.util.RssIcon
import com.example.headlinehunter.core.presentation.util.toText
import com.example.headlinehunter.ui.theme.HeadlineHunterTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDialogUI(
    modifier: Modifier = Modifier,
    channel: Channel? = null,
    feedLink: String = "",
    linkError: DataError? = null,
    onSearchClick: () -> Unit = {},
    onDismissClick: () -> Unit = {},
    onValueChanged: (String) -> Unit = {},
    onSubscribeClick: () -> Unit = {}
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .padding(10.dp, 5.dp, 10.dp, 10.dp)

    ) {

        Column(
            modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedContent(
                targetState = channel == null,
                transitionSpec = {
                    (slideInHorizontally { width -> width } + fadeIn()).togetherWith(
                        slideOutHorizontally { width -> -width } + fadeOut())
                }, label = ""
            ) { searchSuccessful ->
                if (searchSuccessful) {

                    Column {
                        Icon(
                            modifier = Modifier
                                .padding(top = 35.dp)
                                .height(50.dp)
                                .fillMaxWidth(),
                            imageVector = RssIcon,
                            contentDescription = stringResource(R.string.rss_icon),
                            tint = MaterialTheme.colorScheme.secondary
                        )
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = stringResource(R.string.add_new_rss_feed),
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .padding(vertical = 5.dp)
                                    .fillMaxWidth(),
                                style = MaterialTheme.typography.headlineSmall,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                            RssFeedTextField(
                                value = feedLink,
                                errorMessage = linkError?.toText(LocalContext.current) ?: "",
                                onValueChange = {
                                    onValueChanged(it)
                                })
                        }
                    }
                } else {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        val context = LocalContext.current
                        SubcomposeAsyncImage(
                            model = ImageRequest.Builder(context)
                                .data(channel?.image)
                                .error(R.drawable.empty_state)
                                .crossfade(400)
                                .build(),
                            contentDescription = stringResource(R.string.channel_image),
                            modifier = modifier
                                .padding(top = 30.dp)
                                .width(140.dp),
                            loading = { LoadingState() }
                        )

                        Text(
                            text = channel?.title ?: "",
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(horizontal = 20.dp, vertical = 16.dp)
                                .fillMaxWidth(),
                            style = MaterialTheme.typography.headlineSmall,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {

                val isFirstScreen = channel == null

                TextButton(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        onDismissClick()
                    }) {
                    Text(
                        text = "Dismiss",
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                }
                TextButton(
                    modifier = Modifier.weight(1f),
                    enabled = feedLink.length > 6,
                    onClick = {
                        if (isFirstScreen) {
                            onSearchClick()
                        } else {
                            onSubscribeClick()
                        }
                    }) {
                    Text(
                        text = if (isFirstScreen) stringResource(R.string.search)
                        else stringResource(R.string.subscribe),
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                }
            }
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
private fun CardPreview() {
    HeadlineHunterTheme {
        CustomDialogUI()
    }

}