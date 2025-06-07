package com.mkirdev.unsplash.data.storages.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = ReactionsTypeEntity.TABLE_NAME)
data class ReactionsTypeEntity(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = LIKED)
    val liked: Int
) {
    companion object {
        const val TABLE_NAME = "reactions_type"
        const val ID = "id"
        const val LIKED = "liked"
    }
}
