package com.mkirdev.unsplash.data.storages.database.dao.base

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mkirdev.unsplash.data.storages.database.dto.base.ReactionsTypeDto
import com.mkirdev.unsplash.data.storages.database.entities.ReactionsTypeEntity

@Dao
interface ReactionsTypeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addReactionsTypes(reactions: List<ReactionsTypeDto>)

    @Query("DELETE FROM ${ReactionsTypeEntity.TABLE_NAME}")
    suspend fun deleteReactionsTypes()
}