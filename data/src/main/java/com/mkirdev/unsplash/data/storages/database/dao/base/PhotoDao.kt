package com.mkirdev.unsplash.data.storages.database.dao.base

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mkirdev.unsplash.data.storages.database.dto.base.PhotoDto

@Dao
interface PhotoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPhotos(photos: List<PhotoDto>)

    @Query("DELETE FROM photo")
    suspend fun deletePhotos()

}