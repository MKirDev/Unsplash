package com.mkirdev.unsplash.data.storages.database.dto.base

import androidx.room.ColumnInfo

data class PhotoCollectionDto(
    @ColumnInfo(name = PHOTO_ID)
    val photoId: String,
    @ColumnInfo(name = COLLECTION_ID)
    val collectionId: String
) {
    companion object {
        const val PHOTO_ID = "photo_id"
        const val COLLECTION_ID = "collection_id"
    }
}