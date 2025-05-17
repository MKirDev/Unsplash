package com.mkirdev.unsplash.collection_item.models

import androidx.compose.runtime.Immutable
@Immutable
data class CollectionUserModel(
    val name: String,
    val username: String,
    val userImage: String,
)