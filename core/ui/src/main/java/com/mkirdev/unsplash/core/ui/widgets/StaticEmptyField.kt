package com.mkirdev.unsplash.core.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun StaticEmptyField(modifier: Modifier) {
    Column(Modifier.wrapContentSize()) {
        Spacer(modifier = modifier)
    }
}

@Preview
@Composable
fun StaticEmptyFieldPreview() {
    StaticEmptyField(Modifier.fillMaxSize())
}