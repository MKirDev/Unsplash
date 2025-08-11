package com.mkirdev.unsplash.data.storages.database.dto.user

import androidx.room.ColumnInfo
import com.mkirdev.unsplash.data.storages.database.dto.base.ProfileDto

data class UserProfileDto(
    @ColumnInfo(name = PROFILE_ID)
    override val profileId: String,
    @ColumnInfo(name = USERNAME)
    override val username: String,
    @ColumnInfo(name = NAME)
    override val name: String,
    @ColumnInfo(name = FIRST_NAME)
    override val firstName: String,
    @ColumnInfo(name = LAST_NAME)
    override val lastName: String,
    @ColumnInfo(name = IMAGE_URL)
    override val imageUrl: String,
    @ColumnInfo(name = TOTAL_LIKES)
    override val totalLikes: Int,
    @ColumnInfo(name = BIO)
    override val bio: String?,
    @ColumnInfo(name = LOCATION)
    override val location: String?,
    @ColumnInfo(name = DOWNLOADS)
    override val downloads: Int,
    @ColumnInfo(name = EMAIL)
    override val email: String?
) : ProfileDto {
    companion object {
        const val PROFILE_ID = "profile_id"
        const val USERNAME = "username"
        const val NAME = "name"
        const val FIRST_NAME = "first_name"
        const val LAST_NAME = "last_name"
        const val IMAGE_URL = "image_url"
        const val TOTAL_LIKES = "total_likes"
        const val BIO = "bio"
        const val LOCATION = "location"
        const val DOWNLOADS = "downloads"
        const val EMAIL = "email"
    }
}