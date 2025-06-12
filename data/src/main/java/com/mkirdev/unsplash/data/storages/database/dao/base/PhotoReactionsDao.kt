package com.mkirdev.unsplash.data.storages.database.dao.base

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mkirdev.unsplash.data.storages.database.entities.PhotoReactionsEntity

@Dao
interface PhotoReactionsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPhotoReactions(photoReactions: List<PhotoReactionsEntity>)

    @Query("DELETE FROM ${PhotoReactionsEntity.TABLE_NAME}")
    fun deletePhotoReactions()
}