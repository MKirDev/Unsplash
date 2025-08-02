package com.mkirdev.unsplash.domain.models

data class Collection(
    val id: String,
    val title: String,
    val description: String,
    val totalPhotos: Int,
    val user: User,
    val coverPhotoUrl: String,
    val previewPhotoUrl: String
)