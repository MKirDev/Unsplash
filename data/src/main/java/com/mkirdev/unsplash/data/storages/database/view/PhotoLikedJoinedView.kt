package com.mkirdev.unsplash.data.storages.database.view

import androidx.room.ColumnInfo
import androidx.room.DatabaseView

@DatabaseView(
    value = "SELECT " +
            "p.id AS id, p.photo_id AS photo_id, p.width AS width, p.height AS height, " +
            "p.image_url AS image_url, p.download_link AS download_link, p.html_link AS html_link, " +
            "p.likes AS likes, rt.liked AS liked, " +
            "u.user_id AS user_id, u.user_full_name AS user_full_name, u.user_username AS user_username, " +
            "u.user_image_url AS user_image_url, u.user_bio AS user_bio, u.user_location AS user_location " +
            "FROM photo_liked p " +
            "JOIN photo_reactions_liked pr ON pr.photo_id = p.photo_id " +
            "JOIN reactions_liked rt ON rt.photo_id = pr.reactions_id " +
            "JOIN user_liked u ON u.user_id = p.user_id " +
            "WHERE rt.liked = 1",
    viewName = PhotoLikedJoinedView.VIEW_NAME
)
data class PhotoLikedJoinedView(
    @ColumnInfo(name = ID)
    val position: Int,
    @ColumnInfo(name = PHOTO_ID)
    val photoId: String,
    @ColumnInfo(name = WIDTH)
    val width: Int,
    @ColumnInfo(name = HEIGHT)
    val height: Int,
    @ColumnInfo(name = PHOTO_IMAGE_URL)
    val imageUrl: String,
    @ColumnInfo(name = PHOTO_DOWNLOAD_LINK)
    val downloadLink: String,
    @ColumnInfo(name = PHOTO_HTML_LINK)
    val htmlLink: String,
    @ColumnInfo(name = LIKES)
    val likes: Int,
    @ColumnInfo(name = LIKED)
    val likedByUser: Int,
    @ColumnInfo(name = USER_ID)
    val userId: String,
    @ColumnInfo(name = FULL_NAME)
    val fullName: String,
    @ColumnInfo(name = USERNAME)
    val username: String,
    @ColumnInfo(name = USER_IMAGE_URL)
    val userImageUrl: String,
    @ColumnInfo(name = BIO)
    val bio: String?,
    @ColumnInfo(name = LOCATION)
    val location: String?
) {
    companion object {
        const val VIEW_NAME = "liked_photos_view"
        const val ID = "id"
        const val PHOTO_ID = "photo_id"
        const val WIDTH = "width"
        const val HEIGHT = "height"
        const val PHOTO_IMAGE_URL = "image_url"
        const val PHOTO_DOWNLOAD_LINK = "download_link"
        const val PHOTO_HTML_LINK = "html_link"
        const val LIKES = "likes"
        const val LIKED = "liked"
        const val USER_ID = "user_id"
        const val FULL_NAME = "user_full_name"
        const val USERNAME = "user_username"
        const val USER_IMAGE_URL = "user_image_url"
        const val BIO = "user_bio"
        const val LOCATION = "user_location"
    }
}