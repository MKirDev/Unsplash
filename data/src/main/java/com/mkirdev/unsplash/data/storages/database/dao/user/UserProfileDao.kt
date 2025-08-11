package com.mkirdev.unsplash.data.storages.database.dao.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mkirdev.unsplash.data.storages.database.dto.user.UserProfileDto
import com.mkirdev.unsplash.data.storages.database.entities.user.UserProfileEntity

@Dao
interface UserProfileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUser(user: UserProfileEntity)

    @Query("SELECT * FROM ${UserProfileEntity.TABLE_NAME}")
    fun getUser(): UserProfileDto?

    @Query(
        "UPDATE ${UserProfileEntity.TABLE_NAME} " +
                "SET ${UserProfileEntity.TOTAL_LIKES} = ${UserProfileEntity.TOTAL_LIKES} + 1 " +
                "WHERE ${UserProfileEntity.PROFILE_ID} == '${UserProfileEntity.CURRENT_USER}'"
    )
    fun addLike()

    @Query(
        "UPDATE ${UserProfileEntity.TABLE_NAME} " +
                "SET ${UserProfileEntity.TOTAL_LIKES} = ${UserProfileEntity.TOTAL_LIKES} - 1 " +
                "WHERE ${UserProfileEntity.PROFILE_ID} == '${UserProfileEntity.CURRENT_USER}'"
    )
    fun removeLike()

    @Query("DELETE FROM ${UserProfileEntity.TABLE_NAME}")
    fun deleteUser()
}