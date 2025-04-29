package com.mkirdev.unsplash.core.ui.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mkirdev.unsplash.core.ui.theme.UnsplashTheme

@Composable
fun StaticEmptyField(modifier: Modifier) {
    Column(Modifier.wrapContentSize()) {
        Spacer(modifier = modifier)
    }
}

@Preview
@Composable
private fun StaticEmptyFieldPreview() {
    UnsplashTheme(dynamicColor = false) {
        StaticEmptyField(Modifier.fillMaxSize())
    }
}