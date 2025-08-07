package com.mkirdev.unsplash.data.storages.database.dao.feed

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mkirdev.unsplash.data.storages.database.dto.feed.PhotoFeedDto
import com.mkirdev.unsplash.data.storages.database.entities.feed.PhotoFeedEntity

@Dao
interface PhotoFeedDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPhotos(photos: List<PhotoFeedEntity>)

    @Query("SELECT * FROM ${PhotoFeedEntity.TABLE_NAME} p WHERE p.${PhotoFeedEntity.PHOTO_ID} = :id")
    fun getPhoto(id: String): PhotoFeedDto?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPhoto(photo: PhotoFeedEntity)

    @Query("UPDATE ${PhotoFeedEntity.TABLE_NAME} SET ${PhotoFeedEntity.LIKES} = :likes WHERE ${PhotoFeedEntity.PHOTO_ID} = :id")
    fun updateLikes(likes: String, id: String)

    @Query("DELETE FROM ${PhotoFeedEntity.TABLE_NAME}")
    fun deletePhotos()

    @Query("DELETE FROM ${PhotoFeedEntity.TABLE_NAME} WHERE ${PhotoFeedEntity.PHOTO_ID} = :id")
    fun deletePhoto(id: String)

}