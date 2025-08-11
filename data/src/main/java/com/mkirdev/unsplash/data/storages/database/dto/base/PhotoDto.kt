package com.mkirdev.unsplash.data.storages.database.dto.base

interface PhotoDto {
    val id: Int
    val photoId: String
    val width: Int
    val height: Int
    val imageUrl: String
    val downloadLink: String
    val htmlLink: String
    val likes: Int
    val userId: String
}