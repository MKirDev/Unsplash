package com.mkirdev.unsplash.domain.repository

import androidx.paging.PagingData
import com.mkirdev.unsplash.domain.models.CurrentUser
import com.mkirdev.unsplash.domain.models.Photo
import com.mkirdev.unsplash.domain.models.User
import kotlinx.coroutines.flow.Flow

interface CurrentUserRepository {
    fun getLikedPhotos(username: String): Flow<PagingData<Photo>>

    suspend fun getUserInfo(): CurrentUser

}