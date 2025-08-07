package com.mkirdev.unsplash.data.storages.database.dao.search

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mkirdev.unsplash.data.storages.database.dto.search.RemoteKeysSearchDto
import com.mkirdev.unsplash.data.storages.database.entities.search.RemoteKeysSearchEntity

@Dao
interface RemoteKeysSearchDao {
    @Query("SELECT * FROM ${RemoteKeysSearchEntity.Companion.TABLE_NAME} rk WHERE rk.${RemoteKeysSearchEntity.Companion.PHOTO_ID} =:id")
    fun getRemoteKeys(id: String): RemoteKeysSearchDto

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    fun addAllRemoteKeys(remoteKeys: List<RemoteKeysSearchEntity>)

    @Query("DELETE FROM ${RemoteKeysSearchEntity.Companion.TABLE_NAME}")
    fun deleteAllRemoteKeys()
}