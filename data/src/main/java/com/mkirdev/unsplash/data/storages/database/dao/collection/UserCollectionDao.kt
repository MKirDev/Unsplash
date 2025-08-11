package com.mkirdev.unsplash.data.storages.database.dao.collection

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mkirdev.unsplash.data.storages.database.entities.collection.UserCollectionEntity

@Dao
interface UserCollectionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUsers(users: List<UserCollectionEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUser(user: UserCollectionEntity)

    @Query("DELETE FROM ${UserCollectionEntity.TABLE_NAME}")
    fun deleteUsers()

    @Query("DELETE FROM ${UserCollectionEntity.TABLE_NAME} WHERE ${UserCollectionEntity.USER_ID} = :id")
    fun deleteUser(id: String)

    @Query("DELETE FROM sqlite_sequence WHERE name = '${UserCollectionEntity.TABLE_NAME}'")
    fun resetIdSequence()
}