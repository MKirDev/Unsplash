package com.mkirdev.unsplash.data.storages.database.dao.base

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mkirdev.unsplash.data.storages.database.dto.base.PhotoDto
import com.mkirdev.unsplash.data.storages.database.entities.PhotoEntity

@Dao
interface PhotoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPhotos(photos: List<PhotoEntity>)

    @Query("SELECT * FROM ${PhotoEntity.TABLE_NAME} p WHERE p.${PhotoEntity.ID} = :id")
    fun getPhoto(id: String): PhotoDto?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPhoto(photo: PhotoEntity)

    @Query("DELETE FROM ${PhotoEntity.TABLE_NAME} WHERE ${PhotoEntity.SEARCH_TYPE} = 0")
    fun deleteFeedPhotos()

    @Query("DELETE FROM ${PhotoEntity.TABLE_NAME} WHERE ${PhotoEntity.SEARCH_TYPE} = 1")
    fun deleteSearchPhotos()

    @Query("DELETE FROM ${PhotoEntity.TABLE_NAME} WHERE ${PhotoEntity.ID} = :id")
    fun deletePhoto(id: String)

}