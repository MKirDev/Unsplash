package com.mkirdev.unsplash.data.storages.database.entities.base

interface UserEntity {
    val id: Int
    val userId: String
    val fullName: String
    val username: String
    val imageUrl: String
    val bio: String?
    val location: String?
}