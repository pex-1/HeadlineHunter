package com.example.headlinehunter.ui.settings

import android.Manifest
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.headlinehunter.R
import com.example.headlinehunter.core.presentation.components.BasicConfirmDeclineDialog
import com.example.headlinehunter.core.presentation.components.HeadlineHunterToolbar
import com.example.headlinehunter.core.presentation.util.DarkModeIcon
import com.example.headlinehunter.core.presentation.util.NotificationIcon
import com.example.headlinehunter.ui.home.HomeAction
import com.example.headlinehunter.ui.settings.components.SettingsSwitchItem
import com.example.headlinehunter.ui.theme.HeadlineHunterTheme
import com.example.headlinehunter.util.hasNotificationPermission
import com.example.headlinehunter.util.openAppSettings
import com.example.headlinehunter.util.shouldShowNotificationPermissionRationale
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsScreenRoot(
    onBackClick: () -> Unit,
    viewModel: SettingsViewModel = koinViewModel()
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    SettingsScreen(
        state = state,
        onAction = { action ->
            if (action == HomeAction.OnBackClick) {
                onBackClick()
            } else viewModel.onAction(action)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    state: SettingsState,
    onAction: (SettingsAction) -> Unit,
) {

    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                onAction(SettingsAction.OnEnableNotifications)
            }
        }
    )

    if (state.showNotificationSettingsDialog) {
        val activity = context as ComponentActivity
        BasicConfirmDeclineDialog(
            title = stringResource(R.string.permission_permanently_declined),
            confirmText = stringResource(R.string.yes),
            declineText = stringResource(R.string.no),
            onConfirmButtonClick = {
                activity.openAppSettings()
                onAction(SettingsAction.OnShowGoToSettingsDialog)
            },
            onDismissButtonClick = {
                onAction(SettingsAction.OnShowGoToSettingsDialog)
            }
        )
    }

    Column {
        HeadlineHunterToolbar(title = stringResource(R.string.headline_hunter), menuItems = listOf())

        SettingsSwitchItem(
            action = stringResource(R.string.notifications),
            icon = NotificationIcon,
            checked = state.notificationsEnabled
        ) {
            val activity = context as ComponentActivity
            if (state.notificationsEnabled || activity.hasNotificationPermission()) {
                onAction(SettingsAction.OnEnableNotifications)
            } else {
                if (activity.shouldShowNotificationPermissionRationale()) {
                    launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
                } else {
                    onAction(SettingsAction.OnShowGoToSettingsDialog)
                }
            }
        }

        SettingsSwitchItem(
            action = stringResource(R.string.dark_mode),
            icon = DarkModeIcon,
            checked = state.darkModeEnabled
        ) {
            onAction(SettingsAction.OnEnableDarkMode)
        }

    }
}

@Preview
@Composable
private fun SettingsScreenPreview() {
    HeadlineHunterTheme {
        SettingsScreen(SettingsState()) { }
    }
}