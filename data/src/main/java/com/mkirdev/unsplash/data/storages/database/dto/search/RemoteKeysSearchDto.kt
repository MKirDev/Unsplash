package com.mkirdev.unsplash.data.storages.database.dto.search

import androidx.room.ColumnInfo
import com.mkirdev.unsplash.data.storages.database.dto.base.RemoteKeysDto

data class RemoteKeysSearchDto(
    @ColumnInfo(name = ID)
    override val id: Int,
    @ColumnInfo(name = PHOTO_ID)
    override val photoId: String,
    @ColumnInfo(name = PREV_PAGE)
    override val prevPage: Int?,
    @ColumnInfo(name = NEXT_PAGE)
    override val nextPage: Int?
) : RemoteKeysDto {
    companion object {
        const val ID = "id"
        const val PHOTO_ID = "photo_id"
        const val PREV_PAGE = "prev_page"
        const val NEXT_PAGE = "next_page"
    }
}