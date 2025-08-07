package com.mkirdev.unsplash.data.storages.database.dao.search

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mkirdev.unsplash.data.storages.database.entities.search.ReactionsSearchEntity

@Dao
interface ReactionsSearchDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addReactionsTypes(reactions: List<ReactionsSearchEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addReactionType(reaction: ReactionsSearchEntity)

    @Query(
        "UPDATE ${ReactionsSearchEntity.TABLE_NAME} " +
                "SET ${ReactionsSearchEntity.LIKED} = 1 " +
                "WHERE ${ReactionsSearchEntity.PHOTO_ID} = :id"
    )
    fun likePhoto(id: String)

    @Query(
        "UPDATE ${ReactionsSearchEntity.TABLE_NAME} " +
                "SET ${ReactionsSearchEntity.LIKED} = 0 " +
                "WHERE ${ReactionsSearchEntity.PHOTO_ID} = :id"
    )
    fun unlikePhoto(id: String)

    @Query("DELETE FROM ${ReactionsSearchEntity.TABLE_NAME}")
    fun deleteReactionsTypes()

    @Query("DELETE FROM ${ReactionsSearchEntity.TABLE_NAME} " +
            "WHERE ${ReactionsSearchEntity.PHOTO_ID} = :id")
    fun deleteReactionsType(id: String)
}