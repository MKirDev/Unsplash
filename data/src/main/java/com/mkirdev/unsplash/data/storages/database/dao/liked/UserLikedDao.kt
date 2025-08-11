package com.mkirdev.unsplash.data.storages.database.dao.liked

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mkirdev.unsplash.data.storages.database.entities.liked.UserLikedEntity

@Dao
interface UserLikedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUsers(users: List<UserLikedEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUser(user: UserLikedEntity)

    @Query("DELETE FROM ${UserLikedEntity.TABLE_NAME}")
    fun deleteUsers()

    @Query("DELETE FROM ${UserLikedEntity.TABLE_NAME} WHERE ${UserLikedEntity.USER_ID} = :id")
    fun deleteUser(id: String)

    @Query("DELETE FROM sqlite_sequence WHERE name = '${UserLikedEntity.TABLE_NAME}'")
    fun resetIdSequence()
}