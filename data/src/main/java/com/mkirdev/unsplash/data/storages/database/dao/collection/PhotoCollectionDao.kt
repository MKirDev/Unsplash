package com.mkirdev.unsplash.data.storages.database.dao.collection

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mkirdev.unsplash.data.storages.database.dto.collection.PhotoCollectionDto
import com.mkirdev.unsplash.data.storages.database.entities.collection.PhotoCollectionEntity

@Dao
interface PhotoCollectionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPhotos(photos: List<PhotoCollectionEntity>)

    @Query("SELECT * FROM ${PhotoCollectionEntity.TABLE_NAME} p WHERE p.${PhotoCollectionEntity.PHOTO_ID} = :id")
    fun getPhoto(id: String): PhotoCollectionDto?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPhoto(photo: PhotoCollectionEntity)

    @Query("UPDATE ${PhotoCollectionEntity.TABLE_NAME} SET ${PhotoCollectionEntity.LIKES} = :likes WHERE ${PhotoCollectionEntity.PHOTO_ID} = :id")
    fun updateLikes(likes: String, id: String)

    @Query("DELETE FROM ${PhotoCollectionEntity.TABLE_NAME}")
    fun deletePhotos()

    @Query("DELETE FROM ${PhotoCollectionEntity.TABLE_NAME} WHERE ${PhotoCollectionEntity.PHOTO_ID} = :id")
    fun deletePhoto(id: String)

}