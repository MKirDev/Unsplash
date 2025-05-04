package com.mkirdev.unsplash.core.ui.widgets

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.mkirdev.unsplash.core.ui.R
import com.mkirdev.unsplash.core.ui.theme.UnsplashTheme
import com.mkirdev.unsplash.core.ui.theme.image_size_28

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun UserImageMedium(
    imageUrl: String
) {
    GlideImage(
        model = imageUrl,
        contentDescription = stringResource(id = R.string.user_image),
        modifier = Modifier.size(image_size_28)
    )
}

@Preview
@Composable
private fun UserImageMediumPreview() {
    UnsplashTheme(dynamicColor = false) {
        UserImageMedium(imageUrl = "https://images.unsplash.com/photo-1738807992185-76ab3a0573c4?q=80&w=1171&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D")
    }
}