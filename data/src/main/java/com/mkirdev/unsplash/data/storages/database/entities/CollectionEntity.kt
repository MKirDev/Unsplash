package com.mkirdev.unsplash.data.storages.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = CollectionEntity.TABLE_NAME)
data class CollectionEntity(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = NAME)
    val name: String,
    @ColumnInfo(name = COLLECTION_ID)
    val collectionId: String
) {
    companion object {
        const val TABLE_NAME = "collection"
        const val ID = "id"
        const val NAME = "name"
        const val COLLECTION_ID = "collection_id"
    }
}