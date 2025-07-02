package com.mkirdev.unsplash.domain.repository

import androidx.paging.PagingData
import com.mkirdev.unsplash.domain.models.Photo
import kotlinx.coroutines.flow.Flow

interface PhotosRepository {
    fun getPhotos(): Flow<PagingData<Photo>>

    fun searchPhotos(query: String): Flow<PagingData<Photo>>

    suspend fun getPhoto(id: String): Photo

    suspend fun likePhotoLocal(photoId: String)

    suspend fun unlikePhotoLocal(photoId: String)

    suspend fun likePhotoRemote(photoId: String)
    suspend fun unlikePhotoRemote(photoId: String)

    fun getLikedPhoto(): Flow<String>

    fun getUnlikedPhoto(): Flow<String>

    suspend fun clearPhotosStorage()

}