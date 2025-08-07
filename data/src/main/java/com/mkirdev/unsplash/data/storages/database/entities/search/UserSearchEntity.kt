package com.mkirdev.unsplash.data.storages.database.entities.search

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = UserSearchEntity.TABLE_NAME,
    indices = [Index(value = [UserSearchEntity.USER_ID], unique = true)]
)
data class UserSearchEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = USER_ID)
    val userId: String,
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
        const val TABLE_NAME = "user_search"
        const val ID = "id"
        const val USER_ID = "user_id"
        const val FULL_NAME = "user_full_name"
        const val USERNAME = "user_username"
        const val IMAGE_URL = "user_image_url"
        const val BIO = "user_bio"
        const val LOCATION = "user_location"
    }
}
