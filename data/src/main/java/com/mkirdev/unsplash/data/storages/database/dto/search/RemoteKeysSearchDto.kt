package com.mkirdev.unsplash.data.storages.database.dto.search

import androidx.room.ColumnInfo

data class RemoteKeysSearchDto(
    @ColumnInfo(name = ID)
    val id: Int,
    @ColumnInfo(name = PHOTO_ID)
    val photoId: String,
    @ColumnInfo(name = PREV_PAGE)
    val prevPage: Int?,
    @ColumnInfo(name = NEXT_PAGE)
    val nextPage: Int?
) {
    companion object {
        const val ID = "id"
        const val PHOTO_ID = "photo_id"
        const val PREV_PAGE = "prev_page"
        const val NEXT_PAGE = "next_page"
    }
}