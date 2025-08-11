package com.mkirdev.unsplash.data.storages.database.entities.feed

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.mkirdev.unsplash.data.storages.database.entities.base.PhotoEntity

@Entity(
    tableName = PhotoFeedEntity.TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = UserFeedEntity::class,
            parentColumns = [UserFeedEntity.USER_ID],
            childColumns = [PhotoFeedEntity.USER_ID],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(PhotoFeedEntity.USER_ID),
        Index(value = [PhotoFeedEntity.PHOTO_ID], unique = true)
    ]
)
data class PhotoFeedEntity(
    @PrimaryKey(autoGenerate = true)
    override val id: Int = 0,
    @ColumnInfo(name = PHOTO_ID)
    override val photoId: String,
    @ColumnInfo(name = WIDTH)
    override val width: Int,
    @ColumnInfo(name = HEIGHT)
    override val height: Int,
    @ColumnInfo(name = IMAGE_URL)
    override val imageUrl: String,
    @ColumnInfo(name = DOWNLOAD_LINK)
    override val downloadLink: String,
    @ColumnInfo(name = HTML_LINK)
    override val htmlLink: String,
    @ColumnInfo(name = LIKES)
    override val likes: Int,
    @ColumnInfo(name = USER_ID)
    override val userId: String
) : PhotoEntity {
    companion object {
        const val TABLE_NAME = "photo_feed"
        const val ID = "id"

        const val PHOTO_ID = "photo_id"
        const val WIDTH = "width"
        const val HEIGHT = "height"
        const val IMAGE_URL = "image_url"
        const val DOWNLOAD_LINK = "download_link"
        const val HTML_LINK = "html_link"
        const val LIKES = "likes"
        const val USER_ID = "user_id"
    }
}