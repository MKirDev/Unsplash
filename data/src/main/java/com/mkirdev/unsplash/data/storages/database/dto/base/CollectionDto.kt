package com.mkirdev.unsplash.data.storages.database.dto.base

import androidx.room.ColumnInfo

data class CollectionDto(
    @ColumnInfo(name = ID)
    val id: String,
    @ColumnInfo(name = NAME)
    val name: String
) {
    companion object {
        const val ID = "id"
        const val NAME = "name"
    }
}
