package com.mkirdev.unsplash.data.storages.database.dao.liked

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mkirdev.unsplash.data.storages.database.dto.liked.PhotoLikedDto
import com.mkirdev.unsplash.data.storages.database.entities.liked.PhotoLikedEntity

@Dao
interface PhotoLikedDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPhotos(photos: List<PhotoLikedEntity>)

    @Query("SELECT * FROM ${PhotoLikedEntity.TABLE_NAME} p WHERE p.${PhotoLikedEntity.PHOTO_ID} = :id")
    fun getPhoto(id: String): PhotoLikedDto?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPhoto(photo: PhotoLikedEntity)

    @Query("UPDATE ${PhotoLikedEntity.TABLE_NAME} SET ${PhotoLikedEntity.LIKES} = :likes WHERE ${PhotoLikedEntity.PHOTO_ID} = :id")
    fun updateLikes(likes: String, id: String)

    @Query("DELETE FROM ${PhotoLikedEntity.TABLE_NAME}")
    fun deletePhotos()

    @Query("DELETE FROM ${PhotoLikedEntity.TABLE_NAME} WHERE ${PhotoLikedEntity.PHOTO_ID} = :id")
    fun deletePhoto(id: String)

    @Query("DELETE FROM sqlite_sequence WHERE name = '${PhotoLikedEntity.TABLE_NAME}'")
    fun resetIdSequence()

}