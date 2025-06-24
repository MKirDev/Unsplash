package com.mkirdev.unsplash.core.ui.widgets

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.size.Precision
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.mkirdev.unsplash.core.ui.R
import com.mkirdev.unsplash.core.ui.theme.UnsplashTheme
import com.mkirdev.unsplash.core.ui.theme.image_size_18

@Composable
fun UserImageSmall(
    imageUrl: String
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .placeholder(drawableResId = R.drawable.ic_outline_insert_photo_24)
            .diskCachePolicy(CachePolicy.ENABLED)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .precision(Precision.AUTOMATIC)
            .build(),
        contentDescription = stringResource(id = R.string.user_image),
        modifier = Modifier.size(image_size_18)
    )
}

@Preview
@Composable
private fun UserImageSmallPreview() {
    UnsplashTheme(dynamicColor = false) {
        UserImageSmall(imageUrl = "https://images.unsplash.com/photo-1738807992185-76ab3a0573c4?q=80&w=1171&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D")
    }
}