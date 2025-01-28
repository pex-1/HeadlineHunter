package com.example.headlinehunter.core.presentation.components

import androidx.compose.foundation.MarqueeAnimationMode
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.headlinehunter.R
import com.example.headlinehunter.core.presentation.util.BackArrowIcon
import com.example.headlinehunter.core.presentation.util.RssIcon
import com.example.headlinehunter.ui.theme.HeadlineHunterTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeadlineHunterToolbar(
    modifier: Modifier = Modifier,
    showBackButton: Boolean = false,
    title: String? = null,
    menuItems: List<MenuItem> = listOf(MenuItem(RssIcon, MenuAction.ADD_RSS_FEEDS)),
    onMenuItemClick: (MenuAction) -> Unit = {},
    onBackClick: () -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
) {

    TopAppBar(title = {
        if (title != null) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp)
                    .basicMarquee(animationMode = MarqueeAnimationMode.Immediately),
                text = title,
                maxLines = 1,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.headlineMedium,
            )
        }
    },
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        ),
        navigationIcon = {
            if (showBackButton) {
                IconButton(onClick = onBackClick) {
                    Box(
                        modifier = Modifier.background(
                            MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.4f)
                        )
                    ) {
                        Icon(
                            modifier = Modifier
                                .padding(4.dp),
                            imageVector = BackArrowIcon,
                            contentDescription = stringResource(R.string.go_back),
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            }
        },
        actions = {
            menuItems.forEach { item ->
                IconButton(onClick = {
                    onMenuItemClick(item.action)
                }) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun HeadlineHunterToolbarPreview() {
    HeadlineHunterTheme {
        HeadlineHunterToolbar(showBackButton = true)
    }
}