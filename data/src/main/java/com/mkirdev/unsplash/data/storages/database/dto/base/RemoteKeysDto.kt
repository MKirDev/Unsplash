package com.mkirdev.unsplash.data.storages.database.dto.base

import androidx.room.ColumnInfo

data class RemoteKeysDto(
    @ColumnInfo(name = ID)
    val id: String,
    @ColumnInfo(name = PREV_PAGE)
    val prevPage: Int?,
    @ColumnInfo(name = NEXT_PAGE)
    val nextPage: Int?
) {
    companion object {
        const val ID = "id"
        const val PREV_PAGE = "prev_page"
        const val NEXT_PAGE = "next_page"
    }
}