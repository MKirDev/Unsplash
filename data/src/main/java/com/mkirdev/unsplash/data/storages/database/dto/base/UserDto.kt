package com.mkirdev.unsplash.data.storages.database.dto.base

import androidx.room.ColumnInfo

data class UserDto(
    @ColumnInfo(name = FULL_NAME)
    val fullName: String,
    @ColumnInfo(name = USERNAME)
    val username: String,
    @ColumnInfo(name = USER_IMAGE_URL)
    val imageUrl: String,
    @ColumnInfo(name = BIO)
    val bio: String?,
    @ColumnInfo(name = LOCATION)
    val location: String?
) {
    companion object {
        const val FULL_NAME = "full_name"
        const val USERNAME = "username"
        const val USER_IMAGE_URL = "user_image_url"
        const val BIO = "bio"
        const val LOCATION = "location"
    }
}