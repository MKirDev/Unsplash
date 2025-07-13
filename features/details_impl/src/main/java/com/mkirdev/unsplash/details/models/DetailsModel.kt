package com.mkirdev.unsplash.details.models

import androidx.compose.runtime.Immutable
import com.mkirdev.unsplash.photo_item.models.PhotoItemModel

@Immutable
data class DetailsModel(
    val photoItemModel: PhotoItemModel,
    val shareLink: String,
    val location: LocationModel?,
    val tags: String?,
    val exif: ExifModel?,
    val bio: String?
)
