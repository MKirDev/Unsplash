package com.mkirdev.unsplash.data.storages.database.dao.base

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mkirdev.unsplash.data.storages.database.dto.base.RemoteKeysDto
import com.mkirdev.unsplash.data.storages.database.entities.RemoteKeysCollectionEntity

@Dao
interface RemoteKeysCollectionDao {
    @Query("SELECT * FROM ${RemoteKeysCollectionEntity.TABLE_NAME} rk WHERE rk.${RemoteKeysCollectionEntity.ID} =:id")
    fun getRemoteKeys(id: String): RemoteKeysDto

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAllRemoteKeys(remoteKeys: List<RemoteKeysCollectionEntity>)

    @Query("DELETE FROM ${RemoteKeysCollectionEntity.TABLE_NAME}")
    fun deleteAllRemoteKeys()
}