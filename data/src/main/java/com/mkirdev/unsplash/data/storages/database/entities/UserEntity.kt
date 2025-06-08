package com.mkirdev.unsplash.data.storages.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = UserEntity.TABLE_NAME)
data class UserEntity(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = NAME)
    val name: String,
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
        const val TABLE_NAME = "user"
        const val ID = "id"
        const val NAME = "name"
        const val USERNAME = "username"
        const val IMAGE_URL = "image_url"
        const val BIO = "bio"
        const val LOCATION = "location"
    }
}
