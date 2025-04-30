package com.mkirdev.unsplash.photo_item.api

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mkirdev.unsplash.photo_item.impl.PhotoItem
import com.mkirdev.unsplash.photo_item.api.models.PhotoItemModel

class PhotoItemFeatureApiImpl : PhotoItemFeatureApi {
    @Composable
    override fun PhotoItemFeature(
        modifier: Modifier,
        photoItemModel: PhotoItemModel,
        onLike: (String) -> Unit,
        onDislike: (String) -> Unit
    ) {
        PhotoItem(
            modifier = modifier,
            photoItemModel = photoItemModel,
            onLike = onLike,
            onRemoveLike = onDislike
        )
    }
}