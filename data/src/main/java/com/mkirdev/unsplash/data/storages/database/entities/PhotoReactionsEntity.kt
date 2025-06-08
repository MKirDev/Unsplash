package com.mkirdev.unsplash.data.storages.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = PhotoReactionsEntity.TABLE_NAME,
    primaryKeys = [PhotoReactionsEntity.PHOTO_ID, PhotoReactionsEntity.REACTIONS_ID],
    foreignKeys = [
        ForeignKey(
            entity = PhotoEntity::class,
            parentColumns = [PhotoEntity.ID],
            childColumns = [PhotoReactionsEntity.PHOTO_ID],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ReactionsTypeEntity::class,
            parentColumns = [ReactionsTypeEntity.ID],
            childColumns = [PhotoReactionsEntity.REACTIONS_ID],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )],
    indices = [
        Index(
            value = [PhotoReactionsEntity.PHOTO_ID, PhotoReactionsEntity.REACTIONS_ID],
            name = PhotoReactionsEntity.PHOTO_REACTIONS_INDEX, unique = true
        ),
        Index(value = [PhotoReactionsEntity.PHOTO_ID]),
        Index(value = [PhotoReactionsEntity.REACTIONS_ID])
    ]
)
data class PhotoReactionsEntity(
    @ColumnInfo(name = PHOTO_ID)
    val photoId: String,
    @ColumnInfo(name = REACTIONS_ID)
    val reactionsId: String
) {
    companion object {
        const val TABLE_NAME = "photo_reactions"
        const val PHOTO_ID = "photo_id"
        const val REACTIONS_ID = "reactions_id"
        const val PHOTO_REACTIONS_INDEX = "photo_reactions_unique"
    }
}