package com.mkirdev.unsplash.collection_details.models

import androidx.compose.runtime.Immutable

@Immutable
data class CollectionDetailsModel(
    val title: String,
    val description: String,
    val totalPhotos: String,
    val previewPhotoUrl: String,
    val username: String
)