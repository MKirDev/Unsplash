package com.mkirdev.unsplash.data.storages.database.dto.feed

import androidx.room.ColumnInfo
import androidx.room.Embedded

data class PhotoFeedJoinedDto(
    @ColumnInfo(name = ID)
    val position: Int,
    @ColumnInfo(name = PHOTO_ID)
    val photoId: String,
    @ColumnInfo(name = WIDTH)
    val width: Int,
    @ColumnInfo(name = HEIGHT)
    val height: Int,
    @ColumnInfo(name = IMAGE_URL)
    val imageUrl: String,
    @ColumnInfo(name = DOWNLOAD_LINK)
    val downloadLink: String,
    @ColumnInfo(name = HTML_LINK)
    val htmlLink: String,
    @ColumnInfo(name = LIKES)
    val likes: Int,
    @ColumnInfo(name = LIKED)
    val likedByUser: Int,
    @Embedded
    val userFeedDto: UserFeedDto,
) {
    companion object {
        const val ID = "id"
        const val PHOTO_ID = "photo_id"
        const val WIDTH = "width"
        const val HEIGHT = "height"
        const val IMAGE_URL = "image_url"
        const val DOWNLOAD_LINK = "download_link"
        const val HTML_LINK = "html_link"
        const val LIKES = "likes"
        const val LIKED = "liked"
    }
}