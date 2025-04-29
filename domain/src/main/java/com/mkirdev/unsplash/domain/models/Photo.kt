package com.mkirdev.unsplash.domain.models

data class Photo(
    val id: String,
    val imageUrl: String,
    val downloadUrl: String,
    val likes: Int,
    val likedByUser: Boolean,
    val user: User,
    val location: Location,
    val exif: Exif,
    val tags: List<String>,
    val downloads: Int
)






