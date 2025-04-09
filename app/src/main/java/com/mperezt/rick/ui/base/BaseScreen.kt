package com.mperezt.rick.ui.base

import androidx.compose.runtime.Composable
import com.mperezt.rick.ui.screens.shared.ErrorScreen
import com.mperezt.rick.ui.screens.shared.LoadingScreen

@Composable
fun BaseScreen(
    isLoading: Boolean,
    error: String? = null,
    onRetry: () -> Unit = {},
    content: @Composable () -> Unit
) {
    when {
        isLoading -> LoadingScreen()
        error != null -> ErrorScreen(error, onRetry)
        else -> content()
    }
}
