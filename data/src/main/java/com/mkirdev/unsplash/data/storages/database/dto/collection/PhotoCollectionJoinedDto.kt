package com.mkirdev.unsplash.data.storages.database.dto.collection

import androidx.room.ColumnInfo
import androidx.room.Embedded
import com.mkirdev.unsplash.data.storages.database.dto.base.PhotoJoinedDto

data class PhotoCollectionJoinedDto(
    @ColumnInfo(name = ID)
    override val position: Int,
    @ColumnInfo(name = PHOTO_ID)
    override val photoId: String,
    @ColumnInfo(name = WIDTH)
    override val width: Int,
    @ColumnInfo(name = HEIGHT)
    override val height: Int,
    @ColumnInfo(name = IMAGE_URL)
    override val imageUrl: String,
    @ColumnInfo(name = DOWNLOAD_LINK)
    override  val downloadLink: String,
    @ColumnInfo(name = HTML_LINK)
    override  val htmlLink: String,
    @ColumnInfo(name = LIKES)
    override val likes: Int,
    @ColumnInfo(name = LIKED)
    override val likedByUser: Int,
    @Embedded
    val userCollectionDto: UserCollectionDto,
    @ColumnInfo(name = COLLECTION_ID)
    val collectionId: String
) : PhotoJoinedDto {
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
        const val COLLECTION_ID = "collection_id"
    }
}