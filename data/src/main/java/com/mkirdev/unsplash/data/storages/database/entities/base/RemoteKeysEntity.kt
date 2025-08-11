package com.mkirdev.unsplash.data.storages.database.entities.base

interface RemoteKeysEntity {
    val id: Int
    val photoId: String
    val prevPage: Int?
    val nextPage: Int?
}