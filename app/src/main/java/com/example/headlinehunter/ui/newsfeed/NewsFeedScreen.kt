package com.example.headlinehunter.ui.newsfeed

import android.Manifest
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.headlinehunter.R
import com.example.headlinehunter.core.presentation.components.BasicConfirmDeclineDialog
import com.example.headlinehunter.core.presentation.components.HeadlineHunterToolbar
import com.example.headlinehunter.core.presentation.components.MenuAction
import com.example.headlinehunter.core.presentation.components.MenuItem
import com.example.headlinehunter.core.presentation.util.AddIcon
import com.example.headlinehunter.ui.newsfeed.components.CustomDialogUI
import com.example.headlinehunter.ui.newsfeed.components.NewsFeedListItem
import com.example.headlinehunter.ui.theme.HeadlineHunterTheme
import com.example.headlinehunter.util.DevicePreviews
import com.example.headlinehunter.util.hasNotificationPermission
import com.example.headlinehunter.util.openAppSettings
import com.example.headlinehunter.util.shouldShowNotificationPermissionRationale
import org.koin.androidx.compose.koinViewModel

@Composable
fun NewsFeedScreenRoot(
    onBackClick: () -> Unit,
    viewModel: NewsFeedViewModel = koinViewModel()
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    NewsFeedScreen(
        state = state,
        onAction = { action ->
            if (action == NewsFeedAction.OnBackClick) {
                onBackClick()
            } else {
                viewModel.onAction(action)
            }
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsFeedScreen(
    state: NewsFeedState,
    onAction: (NewsFeedAction) -> Unit,
) {
    val context = LocalContext.current
    val activity = context as ComponentActivity

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { }
    )

    Column {
        HeadlineHunterToolbar(
            showBackButton = true,
            title = "News Feeds",
            menuItems = listOf(MenuItem(AddIcon, MenuAction.OPEN_ADD_RSS_DIALOG)),
            onMenuItemClick = {
                if (it == MenuAction.OPEN_ADD_RSS_DIALOG) {
                    onAction(NewsFeedAction.OnShowNewFeedDialog(true))
                }
            },
            onBackClick = {
                onAction(NewsFeedAction.OnBackClick)
            }
        )

        if (state.showNewFeedDialog) {
            Dialog(onDismissRequest = {})
            {
                CustomDialogUI(
                    channel = state.channel,
                    feedLink = state.feedLink,
                    linkError = state.linkError,
                    onSearchClick = {
                        onAction(NewsFeedAction.OnNewFeedDialogSearch)
                    },
                    onDismissClick = {
                        onAction(NewsFeedAction.OnShowNewFeedDialog(false))
                    },
                    onValueChanged = {
                        onAction(NewsFeedAction.OnValueChanged(it))
                    },
                    onSubscribeClick = {
                        onAction(NewsFeedAction.OnNewFeedDialogSubscribe)
                    }
                )
            }
        }

        if (state.showConfirmDeleteDialog) {
            BasicConfirmDeclineDialog(
                title = stringResource(R.string.remove, state.channel?.title ?: ""),
                onConfirmButtonClick = {
                    onAction(NewsFeedAction.OnDeleteChannel)
                },
                onDismissButtonClick = {
                    onAction(NewsFeedAction.OnShowConfirmDeleteDialog(null))
                })
        }

        if (state.showNotificationSettingsDialog) {
            BasicConfirmDeclineDialog(
                title = stringResource(R.string.permission_permanently_declined),
                confirmText = stringResource(R.string.yes),
                declineText = stringResource(R.string.no),
                onConfirmButtonClick = {
                    activity.openAppSettings()
                    onAction(NewsFeedAction.OnShowGoToSettingsDialog)
                },
                onDismissButtonClick = {
                    onAction(NewsFeedAction.OnShowGoToSettingsDialog)
                }
            )
        }

        LazyColumn(
            state = rememberLazyListState(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            items(
                items = state.channels,
                key = { it.title }
            ) { channel ->
                NewsFeedListItem(
                    modifier = Modifier.animateItem(),
                    notificationsEnabled = channel.notificationsEnabled,
                    title = channel.title,
                    description = channel.description,
                    onSubscribeClick = {
                        if (activity.hasNotificationPermission()) {
                            onAction(NewsFeedAction.OnSubscribeClick(channel))
                        } else {
                            if (activity.shouldShowNotificationPermissionRationale()) {
                                launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
                            } else {
                                onAction(NewsFeedAction.OnShowGoToSettingsDialog)
                            }
                        }

                    },
                    onDeleteClick = {
                        onAction(NewsFeedAction.OnShowConfirmDeleteDialog(channel))
                    }
                )
            }

        }

    }
}

@DevicePreviews
@Composable
fun SettingsScreenPreview() {
    HeadlineHunterTheme {
        NewsFeedScreen(NewsFeedState()) { }
    }
}