package com.mkirdev.unsplash.data.storages.database.dao.base

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mkirdev.unsplash.data.storages.database.entities.PhotoCollectionEntity

@Dao
interface PhotoCollectionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPhotosCollections(photoCollections: List<PhotoCollectionEntity>)

    @Query("DELETE FROM ${PhotoCollectionEntity.TABLE_NAME}")
    fun deletePhotoCollection()

}