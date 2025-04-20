package com.mperezt.rick.ui.components

import android.app.Activity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import com.mperezt.rick.R
import com.mperezt.rick.ui.models.ErrorType
import com.mperezt.rick.ui.theme.Elevation
import com.mperezt.rick.ui.theme.Padding
import com.mperezt.rick.ui.theme.Radius
import com.mperezt.rick.ui.theme.Space

@Composable
fun ErrorPopup(
    errorType: ErrorType,
    onRetry: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    closeApp: Boolean = false
) {
    val context = LocalContext.current

    Dialog(onDismissRequest = {
        if (closeApp) {
            (context as? Activity)?.finish()
        } else {
            onDismiss()
        }
    }) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(Padding.Base),
            shape = RoundedCornerShape(Radius.ExtraLarge),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = Elevation.Medium
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Padding.Large),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = getTitleForErrorType(errorType),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.error
                )

                Spacer(modifier = Modifier.height(Space.Base))

                Text(
                    text = getMessageForErrorType(errorType),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(Space.Large))

                Button(
                    onClick = onRetry,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = stringResource(R.string.button_retry))
                }

                TextButton(
                    onClick = { (context as? Activity)?.finish() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = stringResource(R.string.button_close))
                }
            }
        }
    }
}

@Composable
fun ErrorPopup(
    errorMessage: String?,
    onRetry: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    closeApp: Boolean = false
) {
    ErrorPopup(
        errorType = ErrorType.fromErrorMessage(errorMessage),
        onRetry = onRetry,
        onDismiss = onDismiss,
        modifier = modifier,
        closeApp = closeApp
    )
}

@Composable
private fun getTitleForErrorType(errorType: ErrorType): String {
    return stringResource(
        when (errorType) {
            is ErrorType.Network -> R.string.error_title_network
            is ErrorType.Server -> R.string.error_title_server
            is ErrorType.NotFound -> R.string.error_title_not_found
            is ErrorType.Generic -> R.string.error_title_generic
        }
    )
}

@Composable
private fun getMessageForErrorType(errorType: ErrorType): String {
    return when (errorType) {
        is ErrorType.Network -> stringResource(R.string.error_message_network)
        is ErrorType.Server -> stringResource(R.string.error_message_server)
        is ErrorType.NotFound -> stringResource(R.string.error_message_not_found)
        is ErrorType.Generic -> errorType.message
    }
}