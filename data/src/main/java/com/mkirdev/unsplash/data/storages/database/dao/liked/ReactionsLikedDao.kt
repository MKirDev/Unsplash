package com.mkirdev.unsplash.data.storages.database.dao.liked

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mkirdev.unsplash.data.storages.database.entities.liked.ReactionsLikedEntity

@Dao
interface ReactionsLikedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addReactionsTypes(reactions: List<ReactionsLikedEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addReactionType(reaction: ReactionsLikedEntity)

    @Query(
        "UPDATE ${ReactionsLikedEntity.TABLE_NAME} " +
                "SET ${ReactionsLikedEntity.LIKED} = 1 " +
                "WHERE ${ReactionsLikedEntity.PHOTO_ID} = :id"
    )
    fun likePhoto(id: String)

    @Query(
        "UPDATE ${ReactionsLikedEntity.TABLE_NAME} " +
                "SET ${ReactionsLikedEntity.LIKED} = 0 " +
                "WHERE ${ReactionsLikedEntity.PHOTO_ID} = :id"
    )
    fun unlikePhoto(id: String)

    @Query("DELETE FROM ${ReactionsLikedEntity.TABLE_NAME}")
    fun deleteReactionsTypes()

    @Query("DELETE FROM ${ReactionsLikedEntity.TABLE_NAME} " +
            "WHERE ${ReactionsLikedEntity.PHOTO_ID} = :id")
    fun deleteReactionsType(id: String)

    @Query("DELETE FROM sqlite_sequence WHERE name = '${ReactionsLikedEntity.TABLE_NAME}'")
    fun resetIdSequence()
}