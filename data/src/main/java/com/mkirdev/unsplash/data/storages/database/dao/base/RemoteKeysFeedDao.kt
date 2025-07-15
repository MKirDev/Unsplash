package com.mkirdev.unsplash.data.storages.database.dao.base

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mkirdev.unsplash.data.storages.database.dto.base.RemoteKeysDto
import com.mkirdev.unsplash.data.storages.database.entities.RemoteKeysFeedEntity

@Dao
interface RemoteKeysFeedDao {
    @Query("SELECT * FROM ${RemoteKeysFeedEntity.TABLE_NAME} rk WHERE rk.${RemoteKeysFeedEntity.ID} =:id")
    fun getRemoteKeys(id: String): RemoteKeysDto

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAllRemoteKeys(remoteKeys: List<RemoteKeysFeedEntity>)

    @Query("DELETE FROM ${RemoteKeysFeedEntity.TABLE_NAME}")
    fun deleteAllRemoteKeys()
}