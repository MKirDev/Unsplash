package com.mkirdev.unsplash.data.storages.database.entities.feed

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = PhotoReactionsFeedEntity.TABLE_NAME,
    primaryKeys = [PhotoReactionsFeedEntity.PHOTO_ID, PhotoReactionsFeedEntity.REACTIONS_ID],
    foreignKeys = [
        ForeignKey(
            entity = PhotoFeedEntity::class,
            parentColumns = [PhotoFeedEntity.PHOTO_ID],
            childColumns = [PhotoReactionsFeedEntity.PHOTO_ID],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ReactionsFeedEntity::class,
            parentColumns = [ReactionsFeedEntity.PHOTO_ID],
            childColumns = [PhotoReactionsFeedEntity.REACTIONS_ID],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )],
    indices = [
        Index(
            value = [PhotoReactionsFeedEntity.PHOTO_ID, PhotoReactionsFeedEntity.REACTIONS_ID],
            name = PhotoReactionsFeedEntity.PHOTO_REACTIONS_INDEX, unique = true
        ),
        Index(value = [PhotoReactionsFeedEntity.PHOTO_ID]),
        Index(value = [PhotoReactionsFeedEntity.REACTIONS_ID])
    ]
)
data class PhotoReactionsFeedEntity(
    @ColumnInfo(name = PHOTO_ID)
    val photoId: String,
    @ColumnInfo(name = REACTIONS_ID)
    val reactionsId: String
) {
    companion object {
        const val TABLE_NAME = "photo_reactions_feed"
        const val PHOTO_ID = "photo_id"
        const val REACTIONS_ID = "reactions_id"
        const val PHOTO_REACTIONS_INDEX = "photo_reactions_feed_unique"
    }
}