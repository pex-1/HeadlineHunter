package com.example.headlinehunter.core.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.headlinehunter.R
import com.example.headlinehunter.ui.theme.HeadlineHunterTheme

@Composable
fun EmptyState() {

    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            painter = painterResource(R.drawable.empty_state),
            contentDescription = null
        )

        Text(
            modifier = Modifier.padding(bottom = 12.dp),
            text = stringResource(R.string.no_data_found),
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            text = stringResource(R.string.empty_state_message),
            style = MaterialTheme.typography.labelMedium,
            textAlign = TextAlign.Center,
            lineHeight = 20.sp
        )
    }
}

@Preview
@Composable
private fun EmptyStatePreview() {
    HeadlineHunterTheme {
        EmptyState()
    }
}