package com.mkirdev.unsplash.data.storages.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = CollectionEntity.TABLE_NAME)
data class CollectionEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    @ColumnInfo(name = NAME)
    val name: String,
) {
    companion object {
        const val TABLE_NAME = "collection"
        const val ID = "id"
        const val NAME = "name"
    }
}