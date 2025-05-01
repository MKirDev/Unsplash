package com.mkirdev.unsplash.core.ui.widgets

import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mkirdev.unsplash.core.ui.theme.UnsplashTheme
import com.mkirdev.unsplash.core.ui.theme.item_width_64

@Composable
fun LoadingIndicator() {
    CircularProgressIndicator(
        modifier = Modifier.width(item_width_64),
        color = MaterialTheme.colorScheme.onPrimaryContainer,
        trackColor = MaterialTheme.colorScheme.primaryContainer,
    )
}

@Preview
@Composable
private fun LoadingIndicatorPreview() {
    UnsplashTheme(dynamicColor = false) {
        LoadingIndicator()
    }
}