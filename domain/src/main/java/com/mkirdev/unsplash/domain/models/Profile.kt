package com.mkirdev.unsplash.domain.models

data class Profile(
    val id: String,
    val userName: String,
    val firstName: String,
    val lastName: String,
    val bio: String?,
    val location: String?,
    val totalLikes: String?,
    val downloads: String?,
    val email: String?
)