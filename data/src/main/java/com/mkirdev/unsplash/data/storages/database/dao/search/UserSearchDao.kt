package com.mkirdev.unsplash.data.storages.database.dao.search

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mkirdev.unsplash.data.storages.database.entities.search.UserSearchEntity

@Dao
interface UserSearchDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUsers(users: List<UserSearchEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUser(user: UserSearchEntity)

    @Query("DELETE FROM ${UserSearchEntity.TABLE_NAME}")
    fun deleteUsers()

    @Query("DELETE FROM ${UserSearchEntity.TABLE_NAME} WHERE ${UserSearchEntity.USER_ID} = :id")
    fun deleteUser(id: String)
}