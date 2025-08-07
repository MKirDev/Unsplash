package com.mkirdev.unsplash.data.storages.database.dao.collection

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mkirdev.unsplash.data.storages.database.dto.collection.RemoteKeysCollectionDto
import com.mkirdev.unsplash.data.storages.database.entities.collection.RemoteKeysCollectionEntity

@Dao
interface RemoteKeysCollectionDao {
    @Query("SELECT * FROM ${RemoteKeysCollectionEntity.Companion.TABLE_NAME} rk WHERE rk.${RemoteKeysCollectionEntity.Companion.PHOTO_ID} =:id ORDER BY ${RemoteKeysCollectionEntity.Companion.ID} ASC")
    fun getRemoteKeys(id: String): RemoteKeysCollectionDto

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    fun addAllRemoteKeys(remoteKeys: List<RemoteKeysCollectionEntity>)

    @Query("DELETE FROM ${RemoteKeysCollectionEntity.Companion.TABLE_NAME}")
    fun deleteAllRemoteKeys()
}