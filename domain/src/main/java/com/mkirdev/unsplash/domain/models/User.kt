package com.mkirdev.unsplash.domain.models

data class User(
    val id: String,
    val name: String,
    val userName: String,
    val imageUrl: String,
    val bio: String,
    val location: String
)
