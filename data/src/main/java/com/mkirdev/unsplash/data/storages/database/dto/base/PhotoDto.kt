package com.mkirdev.unsplash.data.storages.database.dto.base

import androidx.room.ColumnInfo

data class PhotoDto(
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
    @ColumnInfo(name = USER_ID)
    val userId: String,
    @ColumnInfo(name = SEARCH_TYPE)
    val searchType: Int,
    @ColumnInfo(name = POSITION)
    val position: Int
) {
    companion object {
        const val ID = "id"
        const val WIDTH = "width"
        const val HEIGHT = "height"
        const val IMAGE_URL = "image_url"
        const val DOWNLOAD_LINK = "download_link"
        const val LIKES = "likes"
        const val USER_ID = "user_id"
        const val SEARCH_TYPE = "search_type"
        const val POSITION = "position"
    }
}