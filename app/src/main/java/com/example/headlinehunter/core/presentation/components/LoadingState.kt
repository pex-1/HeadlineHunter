package com.example.headlinehunter.core.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoadingState(modifier: Modifier = Modifier) {
    CircularProgressIndicator(
        modifier = modifier.padding(40.dp),
        color = MaterialTheme.colorScheme.primary,
        strokeWidth = 2.dp
    )
}