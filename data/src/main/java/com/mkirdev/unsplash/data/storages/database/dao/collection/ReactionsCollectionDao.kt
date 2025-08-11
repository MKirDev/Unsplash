package com.mkirdev.unsplash.data.storages.database.dao.collection

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mkirdev.unsplash.data.storages.database.entities.collection.ReactionsCollectionEntity

@Dao
interface ReactionsCollectionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addReactionsTypes(reactions: List<ReactionsCollectionEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addReactionType(reaction: ReactionsCollectionEntity)

    @Query(
        "UPDATE ${ReactionsCollectionEntity.TABLE_NAME} " +
                "SET ${ReactionsCollectionEntity.LIKED} = 1 " +
                "WHERE ${ReactionsCollectionEntity.PHOTO_ID} = :id"
    )
    fun likePhoto(id: String)

    @Query(
        "UPDATE ${ReactionsCollectionEntity.TABLE_NAME} " +
                "SET ${ReactionsCollectionEntity.LIKED} = 0 " +
                "WHERE ${ReactionsCollectionEntity.PHOTO_ID} = :id"
    )
    fun unlikePhoto(id: String)

    @Query("DELETE FROM ${ReactionsCollectionEntity.TABLE_NAME}")
    fun deleteReactionsTypes()

    @Query("DELETE FROM ${ReactionsCollectionEntity.TABLE_NAME} " +
            "WHERE ${ReactionsCollectionEntity.PHOTO_ID} = :id")
    fun deleteReactionsType(id: String)

    @Query("DELETE FROM sqlite_sequence WHERE name = '${ReactionsCollectionEntity.TABLE_NAME}'")
    fun resetIdSequence()
}