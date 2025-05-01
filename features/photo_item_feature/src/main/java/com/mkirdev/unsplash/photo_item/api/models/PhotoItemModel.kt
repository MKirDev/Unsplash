package com.mkirdev.unsplash.photo_item.api.models

data class PhotoItemModel(
    val id: String,
    val imageUrl: String,
    val aspectRatioImage: Float,
    val user: UserModel,
    val likes: String,
    val isLiked: Boolean
)
