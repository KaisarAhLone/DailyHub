package com.example.dailyhub.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun DailyHubTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme {
        content()
    }
}