package com.mkirdev.unsplash.data.storages.database.dao.feed

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mkirdev.unsplash.data.storages.database.entities.feed.UserFeedEntity

@Dao
interface UserFeedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUsers(users: List<UserFeedEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUser(user: UserFeedEntity)

    @Query("DELETE FROM ${UserFeedEntity.TABLE_NAME}")
    fun deleteUsers()

    @Query("DELETE FROM ${UserFeedEntity.TABLE_NAME} WHERE ${UserFeedEntity.USER_ID} = :id")
    fun deleteUser(id: String)
}