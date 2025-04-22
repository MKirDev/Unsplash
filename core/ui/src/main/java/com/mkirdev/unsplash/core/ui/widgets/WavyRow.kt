package com.mkirdev.unsplash.core.ui.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import com.mkirdev.unsplash.core.ui.theme.green

@Composable
fun WavyRow(
    modifier: Modifier,
    verticalAlignment: Alignment.Vertical,
    horizontalArrangement: Arrangement.HorizontalOrVertical,
    function: @Composable () -> Unit
) {
    Row(
        modifier = modifier.drawBehind {
            drawArc(
                color = green,
                startAngle = 0f,
                sweepAngle = 180f,
                useCenter = true,
                topLeft = Offset(-size.width * 0.5f, -size.width - 600f),
                size = Size(
                    size.width + size.width * 1f,
                    size.width + size.width * 0.6f
                )
            )
        },
        verticalAlignment = verticalAlignment,
        horizontalArrangement = horizontalArrangement
    ) {
        function()
    }
}
