package com.mkirdev.unsplash.data.storages.database.dao.feed

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mkirdev.unsplash.data.storages.database.entities.feed.ReactionsFeedEntity

@Dao
interface ReactionsFeedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addReactionsTypes(reactions: List<ReactionsFeedEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addReactionType(reaction: ReactionsFeedEntity)

    @Query(
        "UPDATE ${ReactionsFeedEntity.TABLE_NAME} " +
                "SET ${ReactionsFeedEntity.LIKED} = 1 " +
                "WHERE ${ReactionsFeedEntity.PHOTO_ID} = :id"
    )
    fun likePhoto(id: String)

    @Query(
        "UPDATE ${ReactionsFeedEntity.TABLE_NAME} " +
                "SET ${ReactionsFeedEntity.LIKED} = 0 " +
                "WHERE ${ReactionsFeedEntity.PHOTO_ID} = :id"
    )
    fun unlikePhoto(id: String)

    @Query("DELETE FROM ${ReactionsFeedEntity.TABLE_NAME}")
    fun deleteReactionsTypes()

    @Query("DELETE FROM ${ReactionsFeedEntity.TABLE_NAME} " +
            "WHERE ${ReactionsFeedEntity.PHOTO_ID} = :id")
    fun deleteReactionsType(id: String)

    @Query("DELETE FROM sqlite_sequence WHERE name = '${ReactionsFeedEntity.TABLE_NAME}'")
    fun resetIdSequence()
}