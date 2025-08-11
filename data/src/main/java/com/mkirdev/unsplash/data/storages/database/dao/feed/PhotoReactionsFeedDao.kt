package com.mkirdev.unsplash.data.storages.database.dao.feed

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mkirdev.unsplash.data.storages.database.entities.feed.PhotoReactionsFeedEntity

@Dao
interface PhotoReactionsFeedDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPhotoReactions(photoReactions: List<PhotoReactionsFeedEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPhotoReaction(photoReactions: PhotoReactionsFeedEntity)

    @Query("DELETE FROM ${PhotoReactionsFeedEntity.TABLE_NAME}")
    fun deletePhotoReactions()

    @Query("DELETE FROM sqlite_sequence WHERE name = '${PhotoReactionsFeedEntity.TABLE_NAME}'")
    fun resetIdSequence()
}