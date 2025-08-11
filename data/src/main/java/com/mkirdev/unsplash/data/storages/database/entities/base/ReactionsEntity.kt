package com.mkirdev.unsplash.data.storages.database.entities.base

interface ReactionsEntity {
    val id: Int
    val photoId: String
    val liked: Int
}