package com.mkirdev.unsplash.data.storages.database.entities.collection

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = PhotoCollectionEntity.TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = UserCollectionEntity::class,
            parentColumns = [UserCollectionEntity.USER_ID],
            childColumns = [PhotoCollectionEntity.USER_ID],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(PhotoCollectionEntity.USER_ID),
        Index(value = [PhotoCollectionEntity.PHOTO_ID], unique = true)
    ]
)
data class PhotoCollectionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
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
    @ColumnInfo(name = USER_ID)
    val userId: String,
    @ColumnInfo(name = COLLECTION_ID)
    val collectionId: String
) {
    companion object {
        const val TABLE_NAME = "photo_collection"
        const val ID = "id"

        const val PHOTO_ID = "photo_id"
        const val WIDTH = "width"
        const val HEIGHT = "height"
        const val IMAGE_URL = "image_url"
        const val DOWNLOAD_LINK = "download_link"
        const val HTML_LINK = "html_link"
        const val LIKES = "likes"
        const val USER_ID = "user_id"
        const val COLLECTION_ID = "collection_id"
    }
}