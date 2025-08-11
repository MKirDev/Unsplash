package com.mkirdev.unsplash.data.storages.database.dto.feed

import androidx.room.ColumnInfo
import com.mkirdev.unsplash.data.storages.database.dto.base.UserDto

data class UserFeedDto(
    @ColumnInfo(name = ID)
    override val id: String,
    @ColumnInfo(name = FULL_NAME)
    override val fullName: String,
    @ColumnInfo(name = USERNAME)
    override val username: String,
    @ColumnInfo(name = IMAGE_URL)
    override val imageUrl: String,
    @ColumnInfo(name = BIO)
    override val bio: String?,
    @ColumnInfo(name = LOCATION)
    override val location: String?
) : UserDto {
    companion object {
        const val ID = "user_id"
        const val FULL_NAME = "user_full_name"
        const val USERNAME = "user_username"
        const val IMAGE_URL = "user_image_url"
        const val BIO = "user_bio"
        const val LOCATION = "user_location"
    }
}