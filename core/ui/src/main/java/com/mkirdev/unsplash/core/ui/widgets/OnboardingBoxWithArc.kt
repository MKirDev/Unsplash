package com.mkirdev.unsplash.core.ui.widgets

import android.content.res.Configuration
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.toSize
import com.mkirdev.unsplash.core.ui.theme.UnsplashTheme
import com.mkirdev.unsplash.core.ui.theme.green
import com.mkirdev.unsplash.core.ui.theme.padding_10

@Composable
fun OnboardingBoxWithArc(
    modifier: Modifier,
    arcHeightFactor: Float,
    centerOffsetFactor: Float,
    contentAlignment: Alignment,
    function: @Composable () -> Unit
) {

    val arcFactor by remember(arcHeightFactor) {
        derivedStateOf { 1.1f - arcHeightFactor * 0.03f }
    }

    val centerFactor by remember(centerOffsetFactor) {
        derivedStateOf { 0.52f + centerOffsetFactor * 0.03f }
    }

    val animatedArcHeightFactor by animateFloatAsState(
        targetValue = arcFactor, animationSpec = tween(durationMillis = 600),
        label = ""
    )

    val animatedCenterOffsetFactor by animateFloatAsState(
        targetValue = centerFactor, animationSpec = tween(durationMillis = 600),
        label = ""
    )

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    Box(
        modifier = modifier
            .drawBehind {
                val radius = if (isLandscape) {
                    size.width * animatedArcHeightFactor * 1.2f
                } else {
                    size.width * animatedArcHeightFactor
                }
                val centerY =  if (isLandscape) {
                    size.height * 2.6f
                } else {
                    size.height
                }
                drawCircle(
                    color = green.copy(alpha = 0.9f),
                    radius = radius,
                    center = Offset(size.width * animatedCenterOffsetFactor, centerY)
                )
            },
        contentAlignment = contentAlignment
    ) {
        function()
    }
}

@Preview
@Composable
private fun OnboardingBoxWithArcPreview() {
    UnsplashTheme {
        OnboardingBoxWithArc(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
                .padding(horizontal = padding_10)
                .verticalScroll(rememberScrollState()),
            arcHeightFactor = 1.1f,
            centerOffsetFactor = 0.5f,
            contentAlignment = Alignment.TopCenter
        ) {
        }
    }
}