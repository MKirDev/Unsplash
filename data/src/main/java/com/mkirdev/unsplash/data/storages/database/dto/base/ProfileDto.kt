package com.mkirdev.unsplash.data.storages.database.dto.base

interface ProfileDto {
    val profileId: String
    val username: String
    val name: String
    val firstName: String
    val lastName: String
    val imageUrl: String
    val totalLikes: Int
    val bio: String?
    val location: String?
    val downloads: Int?
    val email: String?
}