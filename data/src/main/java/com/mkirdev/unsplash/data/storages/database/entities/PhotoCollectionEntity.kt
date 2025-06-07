package com.mkirdev.unsplash.data.storages.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = PhotoCollectionEntity.TABLE_NAME,
    primaryKeys = [PhotoCollectionEntity.PHOTO_ID, PhotoCollectionEntity.COLLECTION_ID],
    foreignKeys = [
        ForeignKey(
            entity = PhotoEntity::class,
            parentColumns = [PhotoEntity.ID],
            childColumns = [PhotoCollectionEntity.PHOTO_ID],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = CollectionEntity::class,
            parentColumns = [CollectionEntity.ID],
            childColumns = [PhotoCollectionEntity.COLLECTION_ID],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = [PhotoCollectionEntity.PHOTO_ID]),
        Index(value = [PhotoCollectionEntity.COLLECTION_ID])
    ]
)
data class PhotoCollectionEntity(
    @ColumnInfo(name = PHOTO_ID)
    val photoId: String,
    @ColumnInfo(name = COLLECTION_ID)
    val collectionId: String
) {
    companion object {
        const val TABLE_NAME = "photo_collection"
        const val PHOTO_ID = "photo_id"
        const val COLLECTION_ID = "collection_id"
    }
}