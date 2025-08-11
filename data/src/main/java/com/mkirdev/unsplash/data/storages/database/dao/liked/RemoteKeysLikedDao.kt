package com.mkirdev.unsplash.data.storages.database.dao.liked

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mkirdev.unsplash.data.storages.database.dto.liked.RemoteKeysLikedDto
import com.mkirdev.unsplash.data.storages.database.entities.liked.RemoteKeysLikedEntity

@Dao
interface RemoteKeysLikedDao {
    @Query("SELECT * FROM ${RemoteKeysLikedEntity.TABLE_NAME} rk WHERE rk.${RemoteKeysLikedEntity.PHOTO_ID} =:id ORDER BY ${RemoteKeysLikedEntity.ID} ASC")
    fun getRemoteKeys(id: String): RemoteKeysLikedDto

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAllRemoteKeys(remoteKeys: List<RemoteKeysLikedEntity>)

    @Query("DELETE FROM ${RemoteKeysLikedEntity.TABLE_NAME} WHERE ${RemoteKeysLikedEntity.PHOTO_ID} = :id")
    fun deleteRemoteKey(id: String)
    @Query("DELETE FROM ${RemoteKeysLikedEntity.TABLE_NAME}")
    fun deleteAllRemoteKeys()

    @Query("DELETE FROM sqlite_sequence WHERE name = '${RemoteKeysLikedEntity.TABLE_NAME}'")
    fun resetIdSequence()
}