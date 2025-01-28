package com.example.headlinehunter.core.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.headlinehunter.R
import com.example.headlinehunter.ui.theme.HeadlineHunterTheme

@Composable
fun BasicConfirmDeclineDialog(
    title: String,
    confirmText: String = stringResource(R.string.delete),
    declineText: String = stringResource(R.string.cancel),
    onConfirmButtonClick: () -> Unit,
    onDismissButtonClick: () -> Unit
) {
    AlertDialog(
        modifier = Modifier,
        onDismissRequest = {
            onDismissButtonClick()
        },
        title = {
            Text(
                modifier = Modifier.padding(top = 12.dp),
                textAlign = TextAlign.Center,
                text = title,
                style = MaterialTheme.typography.bodyLarge
            )
        },
        confirmButton = {
            TextButton(onClick = {
                onConfirmButtonClick()
            }) {
                Text(
                    text = confirmText,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.labelMedium
                )
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onDismissButtonClick()
            }) {
                Text(
                    text = declineText,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.tertiary,
                )
            }
        }
    )
}

@Preview
@Composable
private fun ConfirmDeleteDialogPreview() {
    HeadlineHunterTheme {
        BasicConfirmDeclineDialog(
            title = "Are you sure you want to delete ABC News: International?",
            onConfirmButtonClick = {}) { }
    }
}