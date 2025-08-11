package com.mkirdev.unsplash.data.storages.database.entities.collection

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.mkirdev.unsplash.data.storages.database.entities.base.PhotoReactionsEntity


@Entity(
    tableName = PhotoReactionsCollectionEntity.TABLE_NAME,
    primaryKeys = [PhotoReactionsCollectionEntity.PHOTO_ID, PhotoReactionsCollectionEntity.REACTIONS_ID],
    foreignKeys = [
        ForeignKey(
            entity = PhotoCollectionEntity::class,
            parentColumns = [PhotoCollectionEntity.PHOTO_ID],
            childColumns = [PhotoReactionsCollectionEntity.PHOTO_ID],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ReactionsCollectionEntity::class,
            parentColumns = [ReactionsCollectionEntity.PHOTO_ID],
            childColumns = [PhotoReactionsCollectionEntity.REACTIONS_ID],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )],
    indices = [
        Index(
            value = [PhotoReactionsCollectionEntity.PHOTO_ID, PhotoReactionsCollectionEntity.REACTIONS_ID],
            name = PhotoReactionsCollectionEntity.PHOTO_REACTIONS_INDEX, unique = true
        ),
        Index(value = [PhotoReactionsCollectionEntity.PHOTO_ID]),
        Index(value = [PhotoReactionsCollectionEntity.REACTIONS_ID])
    ]
)
data class PhotoReactionsCollectionEntity(
    @ColumnInfo(name = PHOTO_ID)
    override val photoId: String,
    @ColumnInfo(name = REACTIONS_ID)
    override val reactionsId: String
) : PhotoReactionsEntity {
    companion object {
        const val TABLE_NAME = "photo_reactions_collection"
        const val PHOTO_ID = "photo_id"
        const val REACTIONS_ID = "reactions_id"
        const val PHOTO_REACTIONS_INDEX = "photo_reactions_collection_unique"
    }
}