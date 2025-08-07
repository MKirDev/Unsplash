package com.mkirdev.unsplash.data.storages.database.dao.collection

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mkirdev.unsplash.data.storages.database.entities.collection.PhotoReactionsCollectionEntity

@Dao
interface PhotoReactionsCollectionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPhotoReactions(photoReactions: List<PhotoReactionsCollectionEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPhotoReaction(photoReactions: PhotoReactionsCollectionEntity)

    @Query("DELETE FROM ${PhotoReactionsCollectionEntity.TABLE_NAME}")
    fun deletePhotoReactions()
}