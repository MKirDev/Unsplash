package com.mkirdev.unsplash.profile.models

import androidx.compose.runtime.Immutable

@Immutable
data class ProfileModel(
    val id: String,
    val userImage: String,
    val username: String,
    val fullName: String,
    val bio: String?,
    val location: String?,
    val totalLikes: String?,
    val downloads: String?,
    val email: String?
)