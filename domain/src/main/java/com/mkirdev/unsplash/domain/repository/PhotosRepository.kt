package com.mkirdev.unsplash.domain.repository

import androidx.paging.PagingData
import com.mkirdev.unsplash.domain.models.Photo
import kotlinx.coroutines.flow.Flow

interface PhotosRepository {
    suspend fun getPhotos(): Flow<PagingData<Photo>>
}