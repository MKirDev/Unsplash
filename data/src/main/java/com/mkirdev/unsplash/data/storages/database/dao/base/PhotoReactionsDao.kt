package com.mkirdev.unsplash.data.storages.database.dao.base

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mkirdev.unsplash.data.storages.database.dto.base.PhotoReactionsDto

interface PhotoReactionsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPhotoReactions(photoReactions: List<PhotoReactionsDto>)

    @Query("DELETE FROM photo_reactions")
    suspend fun deletePhotoReactions()
}