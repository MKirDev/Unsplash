package com.mkirdev.unsplash.data.storages.database.dto.collection

import androidx.room.ColumnInfo

data class RemoteKeysCollectionDto(
    @ColumnInfo(name = PHOTO_ID)
    val photoId: String,
    @ColumnInfo(name = PREV_PAGE)
    val prevPage: Int?,
    @ColumnInfo(name = NEXT_PAGE)
    val nextPage: Int?
) {
    companion object {
        const val PHOTO_ID = "photo_id"
        const val PREV_PAGE = "prev_page"
        const val NEXT_PAGE = "next_page"
    }
}