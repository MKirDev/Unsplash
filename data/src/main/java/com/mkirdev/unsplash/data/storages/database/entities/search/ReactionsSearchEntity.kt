package com.mkirdev.unsplash.data.storages.database.entities.search

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = ReactionsSearchEntity.TABLE_NAME,
    indices = [Index(value = [PhotoSearchEntity.PHOTO_ID], unique = true)]
)
data class ReactionsSearchEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = PHOTO_ID)
    val photoId: String,
    @ColumnInfo(name = LIKED)
    val liked: Int
) {
    companion object {
        const val TABLE_NAME = "reactions_search"
        const val ID = "id"
        const val PHOTO_ID = "photo_id"
        const val LIKED = "liked"
    }
}