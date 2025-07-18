package com.mkirdev.unsplash.core.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.mkirdev.unsplash.core.ui.R
import com.mkirdev.unsplash.core.ui.theme.UnsplashTheme
import com.mkirdev.unsplash.core.ui.theme.space_10
import com.mkirdev.unsplash.core.ui.theme.space_16
import com.mkirdev.unsplash.core.ui.theme.space_20
import com.mkirdev.unsplash.core.ui.theme.space_30

@Composable
fun ClosableErrorField(
    modifier: Modifier,
    text: String,
    textStyle: TextStyle,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier.background(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(space_20))
        Text(
            text = text.uppercase(),
            modifier = Modifier.weight(1.1f),
            color = MaterialTheme.colorScheme.onError,
            maxLines = 2,
            softWrap = true,
            style = textStyle
        )
        IconButton(
            onClick = onClick,
            modifier = Modifier.weight(0.1f),
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_close_24),
                contentDescription = stringResource(
                    id = R.string.close_notification
                ),
                tint = MaterialTheme.colorScheme.error
            )
        }
        Spacer(modifier = Modifier.width(space_20))
    }
}

@Preview(showBackground = true)
@Composable
private fun ClosableErrorFieldPreview() {
    UnsplashTheme(dynamicColor = false) {
        ClosableErrorField(
            modifier = Modifier.fillMaxWidth(), text = stringResource(id = R.string.download_photo_network_error),
            textStyle = TextStyle.Default
        ) {

        }
    }
}