package com.mkirdev.unsplash.data.storages.database.dao.search

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mkirdev.unsplash.data.storages.database.entities.search.PhotoReactionsSearchEntity

@Dao
interface PhotoReactionsSearchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPhotoReactions(photoReactions: List<PhotoReactionsSearchEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPhotoReaction(photoReactions: PhotoReactionsSearchEntity)

    @Query("DELETE FROM ${PhotoReactionsSearchEntity.TABLE_NAME}")
    fun deletePhotoReactions()
}