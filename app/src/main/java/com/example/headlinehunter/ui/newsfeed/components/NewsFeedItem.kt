package com.example.headlinehunter.ui.newsfeed.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsOff
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.headlinehunter.core.presentation.util.DeleteIcon
import com.example.headlinehunter.ui.theme.HeadlineHunterTheme

@Composable
fun NewsFeedListItem(
    modifier: Modifier = Modifier,
    title: String = "",
    description: String = "",
    notificationsEnabled: Boolean = false,
    onSubscribeClick: () -> Unit,
    onDeleteClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = modifier.padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp),
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 4.dp),
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )

                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    maxLines = 3
                )
            }

            IconButton(onClick = onSubscribeClick) {
                Icon(
                    imageVector = if (!notificationsEnabled) Icons.Filled.NotificationsOff
                    else Icons.Filled.Notifications,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary
                )
            }

            IconButton(onClick = onDeleteClick) {
                Icon(
                    imageVector = DeleteIcon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error
                )

            }
        }
        HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.surfaceVariant)
    }
}

@Preview
@Composable
private fun ArticleListItemPreview() {
    HeadlineHunterTheme {
        NewsFeedListItem(
            title = "ABC News: International",
            description = "No description for this channel",
            onSubscribeClick = {}) {}
    }
}