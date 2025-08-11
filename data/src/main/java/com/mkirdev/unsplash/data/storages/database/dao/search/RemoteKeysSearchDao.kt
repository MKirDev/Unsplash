package com.mkirdev.unsplash.data.storages.database.dao.search

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mkirdev.unsplash.data.storages.database.dto.search.RemoteKeysSearchDto
import com.mkirdev.unsplash.data.storages.database.entities.search.RemoteKeysSearchEntity

@Dao
interface RemoteKeysSearchDao {
    @Query("SELECT * FROM ${RemoteKeysSearchEntity.TABLE_NAME} rk WHERE rk.${RemoteKeysSearchEntity.PHOTO_ID} =:id")
    fun getRemoteKeys(id: String): RemoteKeysSearchDto

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAllRemoteKeys(remoteKeys: List<RemoteKeysSearchEntity>)

    @Query("DELETE FROM ${RemoteKeysSearchEntity.TABLE_NAME}")
    fun deleteAllRemoteKeys()

    @Query("DELETE FROM sqlite_sequence WHERE name = '${RemoteKeysSearchEntity.TABLE_NAME}'")
    fun resetIdSequence()
}