package com.mkirdev.unsplash.collection_item.models

import androidx.compose.runtime.Immutable

@Immutable
data class CollectionItemModel(
    val id: String,
    val title: String,
    val totalPhotos: String,
    val user: CollectionUserModel,
    val coverPhotoUrl: String
)