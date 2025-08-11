package com.mkirdev.unsplash.data.storages.database.entities.liked

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.mkirdev.unsplash.data.storages.database.entities.base.PhotoReactionsEntity

@Entity(
    tableName = PhotoReactionsLikedEntity.TABLE_NAME,
    primaryKeys = [PhotoReactionsLikedEntity.PHOTO_ID, PhotoReactionsLikedEntity.REACTIONS_ID],
    foreignKeys = [
        ForeignKey(
            entity = PhotoLikedEntity::class,
            parentColumns = [PhotoLikedEntity.PHOTO_ID],
            childColumns = [PhotoReactionsLikedEntity.PHOTO_ID],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ReactionsLikedEntity::class,
            parentColumns = [ReactionsLikedEntity.PHOTO_ID],
            childColumns = [PhotoReactionsLikedEntity.REACTIONS_ID],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )],
    indices = [
        Index(
            value = [PhotoReactionsLikedEntity.PHOTO_ID, PhotoReactionsLikedEntity.REACTIONS_ID],
            name = PhotoReactionsLikedEntity.PHOTO_REACTIONS_INDEX, unique = true
        ),
        Index(value = [PhotoReactionsLikedEntity.PHOTO_ID]),
        Index(value = [PhotoReactionsLikedEntity.REACTIONS_ID])
    ]
)
data class PhotoReactionsLikedEntity(
    @ColumnInfo(name = PHOTO_ID)
    override val photoId: String,
    @ColumnInfo(name = REACTIONS_ID)
    override val reactionsId: String
) : PhotoReactionsEntity {
    companion object {
        const val TABLE_NAME = "photo_reactions_liked"
        const val PHOTO_ID = "photo_id"
        const val REACTIONS_ID = "reactions_id"
        const val PHOTO_REACTIONS_INDEX = "photo_reactions_liked_unique"
    }
}