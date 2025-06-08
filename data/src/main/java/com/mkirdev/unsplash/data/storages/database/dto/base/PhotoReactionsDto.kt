package com.mkirdev.unsplash.data.storages.database.dto.base

import androidx.room.ColumnInfo

data class PhotoReactionsDto(
    @ColumnInfo(name = PHOTO_ID)
    val photoId: String,
    @ColumnInfo(name = REACTIONS_ID)
    val reactionsId: String
) {
    companion object {
        const val PHOTO_ID = "photo_id"
        const val REACTIONS_ID = "reactions_id"
    }
}