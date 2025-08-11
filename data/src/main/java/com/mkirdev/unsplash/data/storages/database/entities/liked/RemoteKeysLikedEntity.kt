package com.mkirdev.unsplash.data.storages.database.entities.liked

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mkirdev.unsplash.data.storages.database.entities.base.RemoteKeysEntity

@Entity(tableName = RemoteKeysLikedEntity.TABLE_NAME)
data class RemoteKeysLikedEntity(
    @PrimaryKey(autoGenerate = true)
    override val id: Int = 0,
    @ColumnInfo(name = PHOTO_ID)
    override val photoId: String,
    @ColumnInfo(name = PREV_PAGE)
    override val prevPage: Int?,
    @ColumnInfo(name = NEXT_PAGE)
    override val nextPage: Int?
) : RemoteKeysEntity {
    companion object {
        const val TABLE_NAME = "remote_keys_liked"
        const val ID = "id"
        const val PHOTO_ID = "photo_id"
        const val PREV_PAGE = "prev_page"
        const val NEXT_PAGE = "next_page"
    }
}