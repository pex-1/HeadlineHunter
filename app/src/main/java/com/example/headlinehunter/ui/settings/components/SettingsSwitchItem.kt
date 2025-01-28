package com.example.headlinehunter.ui.settings.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.headlinehunter.core.presentation.util.NotificationIcon
import com.example.headlinehunter.ui.theme.HeadlineHunterTheme

@Composable
fun SettingsSwitchItem(
    action: String,
    icon: ImageVector,
    checked: Boolean = false,
    hasHorizontalDivider: Boolean = true,
    onCheckedChange: () -> Unit
) {

    Column(
        modifier = Modifier.padding(horizontal = 12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Icon(
                imageVector = icon,
                contentDescription = action,
                tint = MaterialTheme.colorScheme.secondary
            )
            Text(
                text = action,
                style = MaterialTheme.typography.bodyLarge,
            )

            Spacer(modifier = Modifier.weight(1f))

            Switch(
                checked = checked,
                onCheckedChange = {
                    onCheckedChange()
                },
                thumbContent = {
                    Icon(
                        imageVector = if (checked) {
                            Icons.Default.Check
                        } else {
                            Icons.Default.Close
                        },
                        contentDescription = null
                    )
                },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colorScheme.background,
                    checkedTrackColor = MaterialTheme.colorScheme.primary
                )

            )
        }

        if (hasHorizontalDivider) {
            HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.surfaceVariant)
        }
    }
}

@Preview
@Composable
private fun SettingsSwitchItemPreview() {
    HeadlineHunterTheme {
        SettingsSwitchItem(
            "Notifications",
            NotificationIcon,
        ) { }
    }
}