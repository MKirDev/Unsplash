package com.mkirdev.unsplash.data.storages.database.dao.liked

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mkirdev.unsplash.data.storages.database.entities.liked.PhotoReactionsLikedEntity

@Dao
interface PhotoReactionsLikedDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPhotoReactions(photoReactions: List<PhotoReactionsLikedEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPhotoReaction(photoReactions: PhotoReactionsLikedEntity)

    @Query("DELETE FROM ${PhotoReactionsLikedEntity.TABLE_NAME}")
    fun deletePhotoReactions()

    @Query("DELETE FROM ${PhotoReactionsLikedEntity.TABLE_NAME} WHERE ${
        PhotoReactionsLikedEntity.PHOTO_ID} = :photoId")
    fun deletePhotoReaction(photoId: String)

    @Query("DELETE FROM sqlite_sequence WHERE name = '${PhotoReactionsLikedEntity.TABLE_NAME}'")
    fun resetIdSequence()
}