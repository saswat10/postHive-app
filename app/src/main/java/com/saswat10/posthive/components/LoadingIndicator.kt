package com.saswat10.posthive.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoadingIndicator() {
    CircularProgressIndicator(
        modifier = Modifier
            .fillMaxSize()
            .padding(128.dp),
        strokeWidth = 8.dp,
        color = MaterialTheme.colorScheme.secondary
    )
}