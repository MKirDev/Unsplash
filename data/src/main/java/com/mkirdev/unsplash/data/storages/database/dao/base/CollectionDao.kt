package com.mkirdev.unsplash.data.storages.database.dao.base

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mkirdev.unsplash.data.storages.database.entities.CollectionEntity

@Dao
interface CollectionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCollections(collections: List<CollectionEntity>)

    @Query("DELETE FROM ${CollectionEntity.TABLE_NAME}")
    suspend fun deleteCollections()
}