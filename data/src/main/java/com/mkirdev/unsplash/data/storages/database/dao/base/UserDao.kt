package com.mkirdev.unsplash.data.storages.database.dao.base

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mkirdev.unsplash.data.storages.database.dto.base.UserDto
import com.mkirdev.unsplash.data.storages.database.entities.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUsers(users: List<UserDto>)

    @Query("DELETE FROM ${UserEntity.TABLE_NAME}")
    suspend fun deleteUsers()
}