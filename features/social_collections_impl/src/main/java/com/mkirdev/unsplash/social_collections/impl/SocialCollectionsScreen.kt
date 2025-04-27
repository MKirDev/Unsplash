package com.mkirdev.unsplash.social_collections.impl

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.mkirdev.unsplash.core.ui.R
import com.mkirdev.unsplash.core.ui.theme.UnsplashTheme
import com.mkirdev.unsplash.core.ui.theme.padding_10
import com.mkirdev.unsplash.core.ui.theme.padding_100
import com.mkirdev.unsplash.core.ui.theme.padding_60
import com.mkirdev.unsplash.core.ui.widgets.OnboardingColumnWithArc

@Composable
fun SocialCollectionsScreen(text: String) {
    OnboardingColumnWithArc(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(horizontal = padding_10)
            .verticalScroll(rememberScrollState()),
        arcHeightFactor = 1.05f,
        centerOffsetFactor = 0.5f,
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.background_cameras),
            contentDescription = stringResource(R.string.background_cameras),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = padding_60),
            contentScale = ContentScale.FillWidth
        )
        Text(
            text = text,
            modifier = Modifier.padding(bottom = padding_100),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.displayMedium
        )
    }

}


@Preview
@Composable
internal fun SocialCollectionsScreenPreview() {
    UnsplashTheme(dynamicColor = false) {
        SocialCollectionsScreen(stringResource(R.string.social_collections))
    }
}