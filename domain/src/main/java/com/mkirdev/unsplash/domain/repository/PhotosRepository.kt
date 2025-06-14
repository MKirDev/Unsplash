package com.mkirdev.unsplash.domain.repository

import androidx.paging.PagingData
import com.mkirdev.unsplash.domain.models.Photo
import kotlinx.coroutines.flow.Flow

interface PhotosRepository {
    fun getPhotos(): Flow<PagingData<Photo>>

    fun searchPhotos(query: String): Flow<PagingData<Photo>>

    suspend fun likePhoto(photoId: String)

    suspend fun unlikePhoto(photoId: String)

    suspend fun clearPhotosStorage()

}