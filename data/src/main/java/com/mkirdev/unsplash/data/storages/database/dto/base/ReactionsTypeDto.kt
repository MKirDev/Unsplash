package com.mkirdev.unsplash.data.storages.database.dto.base

import androidx.room.ColumnInfo

data class ReactionsTypeDto(
    @ColumnInfo(name = ID)
    val id: String,
    @ColumnInfo(name = LIKED)
    val liked: Int
) {
    companion object {
        const val ID = "id"
        const val LIKED = "liked"
    }
}
