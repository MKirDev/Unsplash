package com.mkirdev.unsplash.data.storages.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = RemoteKeysFeedEntity.TABLE_NAME)
data class RemoteKeysFeedEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    @ColumnInfo(name = PREV_PAGE)
    val prevPage: Int?,
    @ColumnInfo(name = NEXT_PAGE)
    val nextPage: Int?
) {
    companion object {
        const val TABLE_NAME = "remote_keys_feed"
        const val ID = "id"
        const val PREV_PAGE = "prev_page"
        const val NEXT_PAGE = "next_page"
    }
}