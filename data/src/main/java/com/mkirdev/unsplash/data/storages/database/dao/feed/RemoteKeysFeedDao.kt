package com.mkirdev.unsplash.data.storages.database.dao.feed

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mkirdev.unsplash.data.storages.database.dto.feed.RemoteKeysFeedDto
import com.mkirdev.unsplash.data.storages.database.entities.feed.RemoteKeysFeedEntity

@Dao
interface RemoteKeysFeedDao {
    @Query("SELECT * FROM ${RemoteKeysFeedEntity.Companion.TABLE_NAME} rk WHERE rk.${RemoteKeysFeedEntity.Companion.PHOTO_ID} =:id")
    fun getRemoteKeys(id: String): RemoteKeysFeedDto

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    fun addAllRemoteKeys(remoteKeys: List<RemoteKeysFeedEntity>)

    @Query("DELETE FROM ${RemoteKeysFeedEntity.Companion.TABLE_NAME}")
    fun deleteAllRemoteKeys()
}