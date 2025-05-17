package com.mkirdev.unsplash.photo_item.models

import androidx.compose.runtime.Immutable

@Immutable
data class UserModel(
    val name: String,
    val username: String,
    val userImage: String,
)