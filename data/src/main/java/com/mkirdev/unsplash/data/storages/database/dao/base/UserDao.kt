package com.mkirdev.unsplash.data.storages.database.dao.base

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mkirdev.unsplash.data.storages.database.entities.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUsers(users: List<UserEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUser(user: UserEntity)

    @Query("DELETE FROM ${UserEntity.TABLE_NAME}")
    fun deleteUsers()

    @Query("DELETE FROM ${UserEntity.TABLE_NAME} WHERE ${UserEntity.ID} = :id")
    fun deleteUser(id: String)
}