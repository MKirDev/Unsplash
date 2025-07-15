package com.mkirdev.unsplash.data.storages.database.dao.base

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mkirdev.unsplash.data.storages.database.dto.base.RemoteKeysDto
import com.mkirdev.unsplash.data.storages.database.entities.RemoteKeysSearchEntity

@Dao
interface RemoteKeysSearchDao {
    @Query("SELECT * FROM ${RemoteKeysSearchEntity.TABLE_NAME} rk WHERE rk.${RemoteKeysSearchEntity.ID} =:id")
    fun getRemoteKeys(id: String): RemoteKeysDto

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAllRemoteKeys(remoteKeys: List<RemoteKeysSearchEntity>)

    @Query("DELETE FROM ${RemoteKeysSearchEntity.TABLE_NAME}")
    fun deleteAllRemoteKeys()
}