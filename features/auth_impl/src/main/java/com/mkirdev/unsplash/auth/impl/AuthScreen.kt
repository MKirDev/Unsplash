package com.mkirdev.unsplash.auth.impl

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.mkirdev.unsplash.core.ui.R
import com.mkirdev.unsplash.core.ui.theme.bodyLargeMedium
import com.mkirdev.unsplash.core.ui.theme.image_size_340
import com.mkirdev.unsplash.core.ui.theme.item_height_54
import com.mkirdev.unsplash.core.ui.theme.padding_100
import com.mkirdev.unsplash.core.ui.theme.padding_30
import com.mkirdev.unsplash.core.ui.widgets.ClosableErrorField
import com.mkirdev.unsplash.core.ui.widgets.StandardButton
import com.mkirdev.unsplash.core.ui.widgets.StaticInfoField
import com.mkirdev.unsplash.core.ui.widgets.WavyRow

@Composable
fun AuthScreen(
    uiState: AuthContract.State,
    onNotificationClick: () -> Unit,
    onAuthClick: () -> Unit,
) {

    Column(modifier = Modifier.fillMaxSize()) {
        WavyRow(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.onSurfaceVariant),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Image(
                painter = painterResource(id = R.drawable.unsplash_logo),
                contentDescription = stringResource(id = R.string.unsplash_logo),
                modifier = Modifier.size(image_size_340)
            )
        }
        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background),
            verticalAlignment = Alignment.Top
        ) {
            StandardButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = padding_100, start = padding_30, end = padding_30),
                text = stringResource(id = R.string.auth),
                textStyle = MaterialTheme.typography.labelLarge,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                onClick = onAuthClick
            )
        }
        when (uiState) {
            is AuthContract.State.Error -> ClosableErrorField(
                modifier = Modifier
                    .height(item_height_54)
                    .testTag(AuthScreenTags.NOTIFICATION_ERROR),
                text = uiState.message,
                textStyle = MaterialTheme.typography.bodyLargeMedium,
                onClick = onNotificationClick
            )

            AuthContract.State.Idle -> Unit

            AuthContract.State.Success ->
                StaticInfoField(
                    modifier = Modifier
                        .height(item_height_54)
                        .testTag(AuthScreenTags.NOTIFICATION_INFO),
                    text = stringResource(id = R.string.successfully_auth),
                    textStyle = MaterialTheme.typography.bodyLargeMedium
                )
        }
    }
}

object AuthScreenTags {
    const val NOTIFICATION_INFO = "AuthScreenTags:NOTIFICATION_INFO"
    const val NOTIFICATION_ERROR = "AuthScreenTags:NOTIFICATION_ERROR"
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AuthScreenPreview() {
    AuthScreen(
        uiState = AuthContract.State.Success,
        onNotificationClick = {},
        onAuthClick = {}
    )
}