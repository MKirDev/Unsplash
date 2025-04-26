package com.mkirdev.unsplash.core.ui.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.tooling.preview.Preview
import com.mkirdev.unsplash.core.ui.theme.UnsplashTheme
import com.mkirdev.unsplash.core.ui.theme.green

@Composable
fun WavyColumn(
    modifier: Modifier,
    function: @Composable () -> Unit
) {
    Column(
        modifier = modifier.drawBehind {
            drawArc(
                color = green,
                startAngle = 0f,
                sweepAngle = 180f,
                useCenter = true,
                topLeft = Offset(0f, -size.height * 0.2f),
                size = Size(
                    size.width,
                    size.height * 0.3f
                )
            )
        },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        function()
    }
}

@Preview
@Composable
fun WavyRowPreview() {
    UnsplashTheme(dynamicColor = false) {
        WavyColumn(
            modifier = Modifier.fillMaxSize()
        ) {

        }
    }
}
