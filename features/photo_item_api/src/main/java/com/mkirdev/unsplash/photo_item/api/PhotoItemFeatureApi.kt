package com.mkirdev.unsplash.photo_item.api

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mkirdev.unsplash.photo_item.api.models.PhotoItemModel

interface PhotoItemFeatureApi {
    @Composable
    fun PhotoItemFeature(
        modifier: Modifier,
        photoItemModel: PhotoItemModel,
        onLike: (String) -> Unit,
        onDislike: (String) -> Unit
    )
}