package com.mkirdev.unsplash.core.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.mkirdev.unsplash.core.ui.R
import com.mkirdev.unsplash.core.ui.theme.UnsplashTheme
import com.mkirdev.unsplash.core.ui.theme.space_10
import com.mkirdev.unsplash.core.ui.theme.space_16

@Composable
fun StaticErrorField(
    modifier: Modifier,
    text: String,
    textStyle: TextStyle
) {
    Row(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(space_16))
        Text(
            text = text.uppercase(),
            modifier = Modifier.weight(1f),
            color = MaterialTheme.colorScheme.onError,
            maxLines = 2,
            softWrap = true,
            style = textStyle
        )
        Spacer(modifier = Modifier.width(space_10))
    }
}

@Preview(showBackground = true)
@Composable
private fun StaticErrorFieldPreview() {
    UnsplashTheme(dynamicColor = false) {
        StaticErrorField(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.download_photo_network_error),
            textStyle = TextStyle.Default
        )
    }
}