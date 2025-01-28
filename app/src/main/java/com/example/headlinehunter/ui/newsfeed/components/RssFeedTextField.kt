package com.example.headlinehunter.ui.newsfeed.components

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.headlinehunter.R
import com.example.headlinehunter.core.presentation.util.ClipboardIcon
import com.example.headlinehunter.core.presentation.util.CloseIcon
import kotlin.text.isEmpty
import kotlin.text.isNotEmpty

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RssFeedTextField(
    readOnly: Boolean = false,
    value: String = "",
    label: String = "",
    onValueChange: (String) -> Unit,
    placeholder: String = stringResource(R.string.enter_rss_feed_url),
    errorMessage: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions.Companion.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
) {
    val clipboardManager = LocalClipboardManager.current

    TextField(
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.Companion.Transparent,
            focusedContainerColor = Color.Companion.Transparent
        ),
        maxLines = 1,
        enabled = !readOnly,
        value = value,
        label = if (label.isEmpty()) null else {
            {
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelMedium
                )
            }
        },
        onValueChange = {
            if (!readOnly) onValueChange(it)
        },
        placeholder = {
            Text(
                text = placeholder,
                color = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.7f),
                style = MaterialTheme.typography.bodyMedium
            )
        },
        isError = errorMessage.isNotEmpty(),
        singleLine = true,
        trailingIcon = {
            if (value.isNotEmpty()) {
                IconButton(onClick = {
                    onValueChange("")
                }) {
                    Icon(
                        imageVector = CloseIcon,
                        contentDescription = stringResource(R.string.clear_rss_link),
                        tint = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.5f),
                    )
                }
            } else {
                IconButton(onClick = {
                    onValueChange(clipboardManager.getText()?.text ?: "")
                }) {
                    Icon(
                        imageVector = ClipboardIcon,
                        contentDescription = stringResource(R.string.paste_rss_link),
                        tint = MaterialTheme.colorScheme.tertiary
                    )
                }
            }
        },
        supportingText = {
            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
    )
}

@Preview
@Composable
private fun Preview() {
    MaterialTheme {
        RssFeedTextField(onValueChange = {})
    }
}
