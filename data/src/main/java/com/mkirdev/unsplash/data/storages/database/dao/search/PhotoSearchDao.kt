package com.mkirdev.unsplash.data.storages.database.dao.search

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mkirdev.unsplash.data.storages.database.dto.search.PhotoSearchDto
import com.mkirdev.unsplash.data.storages.database.entities.search.PhotoSearchEntity

@Dao
interface PhotoSearchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPhotos(photos: List<PhotoSearchEntity>)

    @Query("SELECT * FROM ${PhotoSearchEntity.TABLE_NAME} p WHERE p.${PhotoSearchEntity.PHOTO_ID} = :id")
    fun getPhoto(id: String): PhotoSearchDto?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPhoto(photo: PhotoSearchEntity)

    @Query("UPDATE ${PhotoSearchEntity.TABLE_NAME} SET ${PhotoSearchEntity.LIKES} = :likes WHERE ${PhotoSearchEntity.PHOTO_ID} = :id")
    fun updateLikes(likes: String, id: String)

    @Query("DELETE FROM ${PhotoSearchEntity.TABLE_NAME}")
    fun deletePhotos()

    @Query("DELETE FROM ${PhotoSearchEntity.TABLE_NAME} WHERE ${PhotoSearchEntity.PHOTO_ID} = :id")
    fun deletePhoto(id: String)

    @Query("DELETE FROM sqlite_sequence WHERE name = '${PhotoSearchEntity.TABLE_NAME}'")
    fun resetIdSequence()

}