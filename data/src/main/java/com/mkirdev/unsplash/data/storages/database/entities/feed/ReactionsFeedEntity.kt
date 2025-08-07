package com.mkirdev.unsplash.data.storages.database.entities.feed

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = ReactionsFeedEntity.TABLE_NAME,
    indices = [Index(value = [PhotoFeedEntity.PHOTO_ID], unique = true)]
)
data class ReactionsFeedEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = PHOTO_ID)
    val photoId: String,
    @ColumnInfo(name = LIKED)
    val liked: Int
) {
    companion object {
        const val TABLE_NAME = "reactions_feed"
        const val ID = "id"
        const val PHOTO_ID = "photo_id"
        const val LIKED = "liked"
    }
}