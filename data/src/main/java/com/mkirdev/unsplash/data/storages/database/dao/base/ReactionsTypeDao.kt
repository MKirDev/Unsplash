package com.mkirdev.unsplash.data.storages.database.dao.base

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mkirdev.unsplash.data.storages.database.entities.ReactionsTypeEntity

@Dao
interface ReactionsTypeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addReactionsTypes(reactions: List<ReactionsTypeEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addReactionType(reaction: ReactionsTypeEntity)

    @Query(
        "UPDATE ${ReactionsTypeEntity.TABLE_NAME} " +
                "SET ${ReactionsTypeEntity.LIKED} = 1 " +
                "WHERE ${ReactionsTypeEntity.ID} = :id"
    )
    fun likePhoto(id: String)

    @Query(
        "UPDATE ${ReactionsTypeEntity.TABLE_NAME} " +
                "SET ${ReactionsTypeEntity.LIKED} = 0 " +
                "WHERE ${ReactionsTypeEntity.ID} = :id"
    )
    fun unlikePhoto(id: String)

    @Query("DELETE FROM ${ReactionsTypeEntity.TABLE_NAME}")
    fun deleteReactionsTypes()

    @Query("DELETE FROM ${ReactionsTypeEntity.TABLE_NAME} " +
            "WHERE ${ReactionsTypeEntity.ID} = :id")
    fun deleteReactionsType(id: String)
}