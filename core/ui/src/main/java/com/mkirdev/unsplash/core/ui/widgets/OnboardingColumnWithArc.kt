package com.mkirdev.unsplash.core.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.toSize
import com.mkirdev.unsplash.core.ui.theme.UnsplashTheme
import com.mkirdev.unsplash.core.ui.theme.green
import com.mkirdev.unsplash.core.ui.theme.padding_10

@Composable
fun OnboardingColumnWithArc(
    modifier: Modifier,
    arcHeightFactor: Float,
    centerOffsetFactor: Float,
    verticalArrangement: Arrangement.Vertical,
    horizontalAlignment: Alignment.Horizontal,
    function: @Composable () -> Unit
) {
    var halfSize by remember {
        mutableStateOf(Size(0f, 0f))
    }

    Column(
        modifier = modifier
            .onSizeChanged {
                halfSize = it.toSize() / 2f
            }
            .drawBehind {
                drawCircle(
                    color = green,
                    radius = halfSize.height * arcHeightFactor,
                    center = Offset(size.width * centerOffsetFactor, size.height)
                )
            },
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment
    ) {
        function()
    }
}

@Preview
@Composable
private fun OnboardingColumnWithArcPreview() {
    UnsplashTheme {
        OnboardingColumnWithArc(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
                .padding(horizontal = padding_10)
                .verticalScroll(rememberScrollState()),
            arcHeightFactor = 1.1f,
            centerOffsetFactor = 0.5f,
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
        }
    }
}