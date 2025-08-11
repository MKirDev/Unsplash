package com.mkirdev.unsplash.data.storages.database.dto.base


interface RemoteKeysDto {
    val id: Int
    val photoId: String
    val prevPage: Int?
    val nextPage: Int?
}