package com.mkirdev.unsplash.data.storages.database.entities.search

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.mkirdev.unsplash.data.storages.database.entities.base.PhotoReactionsEntity

@Entity(
    tableName = PhotoReactionsSearchEntity.TABLE_NAME,
    primaryKeys = [PhotoReactionsSearchEntity.PHOTO_ID, PhotoReactionsSearchEntity.REACTIONS_ID],
    foreignKeys = [
        ForeignKey(
            entity = PhotoSearchEntity::class,
            parentColumns = [PhotoSearchEntity.PHOTO_ID],
            childColumns = [PhotoReactionsSearchEntity.PHOTO_ID],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ReactionsSearchEntity::class,
            parentColumns = [ReactionsSearchEntity.PHOTO_ID],
            childColumns = [PhotoReactionsSearchEntity.REACTIONS_ID],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )],
    indices = [
        Index(
            value = [PhotoReactionsSearchEntity.PHOTO_ID, PhotoReactionsSearchEntity.REACTIONS_ID],
            name = PhotoReactionsSearchEntity.PHOTO_REACTIONS_INDEX, unique = true
        ),
        Index(value = [PhotoReactionsSearchEntity.PHOTO_ID]),
        Index(value = [PhotoReactionsSearchEntity.REACTIONS_ID])
    ]
)
data class PhotoReactionsSearchEntity(
    @ColumnInfo(name = PHOTO_ID)
    override val photoId: String,
    @ColumnInfo(name = REACTIONS_ID)
    override val reactionsId: String
) : PhotoReactionsEntity {
    companion object {
        const val TABLE_NAME = "photo_reactions_search"
        const val PHOTO_ID = "photo_id"
        const val REACTIONS_ID = "reactions_id"
        const val PHOTO_REACTIONS_INDEX = "photo_reactions_search_unique"
    }
}