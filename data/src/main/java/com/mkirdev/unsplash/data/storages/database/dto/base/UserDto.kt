package com.mkirdev.unsplash.data.storages.database.dto.base

interface UserDto {
    val id: String
    val fullName: String
    val username: String
    val imageUrl: String
    val bio: String?
    val location: String?
}