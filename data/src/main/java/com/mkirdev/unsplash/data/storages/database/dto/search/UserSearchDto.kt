package com.mkirdev.unsplash.data.storages.database.dto.search

import androidx.room.ColumnInfo

data class UserSearchDto(
    @ColumnInfo(name = ID)
    val id: String,
    @ColumnInfo(name = FULL_NAME)
    val fullName: String,
    @ColumnInfo(name = USERNAME)
    val username: String,
    @ColumnInfo(name = IMAGE_URL)
    val imageUrl: String,
    @ColumnInfo(name = BIO)
    val bio: String?,
    @ColumnInfo(name = LOCATION)
    val location: String?
) {
    companion object {
        const val ID = "user_id"
        const val FULL_NAME = "user_full_name"
        const val USERNAME = "user_username"
        const val IMAGE_URL = "user_image_url"
        const val BIO = "user_bio"
        const val LOCATION = "user_location"
    }
}