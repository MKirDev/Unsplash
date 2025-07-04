package com.mkirdev.unsplash.data.storages.database.dto.collection

import androidx.room.ColumnInfo
import androidx.room.Embedded
import com.mkirdev.unsplash.data.storages.database.dto.base.UserDto

data class PhotoFromCollectionDto(
    @ColumnInfo(name = ID)
    val id: String,
    @ColumnInfo(name = WIDTH)
    val width: Int,
    @ColumnInfo(name = HEIGHT)
    val height: Int,
    @ColumnInfo(name = IMAGE_URL)
    val imageUrl: String,
    @ColumnInfo(name = DOWNLOAD_LINK)
    val downloadLink: String,
    @ColumnInfo(name = LIKES)
    val likes: Int,
    @ColumnInfo(name = LIKED)
    val likedByUser: Int,
    @ColumnInfo(name = COLLECTION)
    val collection: String,
    @Embedded
    val userDto: UserDto
) {

    companion object {
        const val ID = "id"
        const val WIDTH = "width"
        const val HEIGHT = "height"
        const val IMAGE_URL = "image_url"
        const val DOWNLOAD_LINK = "download_link"
        const val LIKES = "likes"
        const val LIKED = "liked"
        const val COLLECTION = "collection_name"
    }
}