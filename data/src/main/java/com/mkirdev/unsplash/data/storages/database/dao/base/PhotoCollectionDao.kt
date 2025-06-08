package com.mkirdev.unsplash.data.storages.database.dao.base

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mkirdev.unsplash.data.storages.database.dto.base.PhotoCollectionDto

interface PhotoCollectionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPhotoCollection(photoCollections: List<PhotoCollectionDto>)

    @Query("DELETE FROM photo_collection")
    suspend fun deletePhotoCollection()
}