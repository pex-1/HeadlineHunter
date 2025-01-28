package com.example.headlinehunter.ui.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.headlinehunter.core.domain.channel.Channel
import com.example.headlinehunter.ui.theme.HeadlineHunterTheme

@Composable
fun ChannelSelect(
    channel: Channel,
    onChannelClick: (Channel) -> Unit
) {

    val interactionSource = remember { MutableInteractionSource() }
    Column(
        modifier = Modifier
            .alpha(if (channel.isSelected) 1f else 0.5f)
            .width(100.dp)
            .clickable(
                onClick = {
                    onChannelClick(channel.copy(isSelected = channel.isSelected.not()))
                },
                indication = null,
                interactionSource = interactionSource
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        ChannelImage(
            modifier = Modifier
                .size(80.dp),
            imageUrl = channel.image,
            shape = CircleShape,
            selected = channel.isSelected
        )
        Text(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            text = channel.title,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview
@Composable
private fun ChannelSelectPreview() {
    HeadlineHunterTheme {
        ChannelSelect(Channel(), {})
    }
}