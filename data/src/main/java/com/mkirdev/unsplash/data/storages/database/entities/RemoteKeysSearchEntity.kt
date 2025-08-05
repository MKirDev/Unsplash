package com.mkirdev.unsplash.data.storages.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = RemoteKeysSearchEntity.TABLE_NAME)
data class RemoteKeysSearchEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = PHOTO_ID)
    val photoId: String,
    @ColumnInfo(name = PREV_PAGE)
    val prevPage: Int?,
    @ColumnInfo(name = NEXT_PAGE)
    val nextPage: Int?
) {
    companion object {
        const val TABLE_NAME = "remote_keys_search"
        const val ID = "id"
        const val PHOTO_ID = "photo_id"
        const val PREV_PAGE = "prev_page"
        const val NEXT_PAGE = "next_page"
    }
}