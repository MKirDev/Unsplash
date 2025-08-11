package com.mkirdev.unsplash.data.storages.database.entities.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.mkirdev.unsplash.data.storages.database.entities.base.ProfileEntity

@Entity(
    tableName = UserProfileEntity.TABLE_NAME,
    indices = [
        Index(value = [UserProfileEntity.PROFILE_ID], unique = true)
    ]
)
data class UserProfileEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = PROFILE_ID)
    override val profileId: String = CURRENT_USER,
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
) : ProfileEntity {
    companion object {
        const val TABLE_NAME = "profile_user"
        const val ID = "id"
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

        internal  const val CURRENT_USER = "current_user"
    }
}