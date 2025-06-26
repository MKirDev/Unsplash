package com.mkirdev.unsplash.photo_item.models

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp

@Immutable
data class PhotoItemModel(
    val id: String,
    val imageUrl: String,
    val width: Dp,
    val height: Dp,
    val aspectRatioImage: Float,
    val user: UserModel,
    val downloadLink: String,
    val downloads: String,
    val likes: String,
    val isLiked: Boolean
)
