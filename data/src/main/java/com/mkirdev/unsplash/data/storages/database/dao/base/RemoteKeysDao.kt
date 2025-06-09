package com.mkirdev.unsplash.data.storages.database.dao.base

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mkirdev.unsplash.data.storages.database.dto.base.RemoteKeysDto
import com.mkirdev.unsplash.data.storages.database.entities.RemoteKeysEntity

interface RemoteKeysDao {
    @Query("SELECT * FROM ${RemoteKeysEntity.TABLE_NAME} rk WHERE rk.${RemoteKeysEntity.ID} =:id")
    fun getRemoteKeys(id: String): RemoteKeysDto

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys: List<RemoteKeysDto>)

    @Query("DELETE FROM ${RemoteKeysEntity.TABLE_NAME}")
    suspend fun deleteAllRemoteKeys()
}