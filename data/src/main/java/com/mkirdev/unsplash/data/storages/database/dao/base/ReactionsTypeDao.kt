package com.mkirdev.unsplash.data.storages.database.dao.base

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mkirdev.unsplash.data.storages.database.dto.base.ReactionsTypeDto

@Dao
interface ReactionsTypeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addReactionsTypes(reactions: List<ReactionsTypeDto>)

    @Query("DELETE FROM reactions_type")
    suspend fun deleteReactionsTypes()
}