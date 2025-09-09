package com.mkirdev.unsplash.domain.repository

import androidx.paging.PagingData
import com.mkirdev.unsplash.domain.models.Photo
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun searchPhotos(query: String): Flow<PagingData<Photo>>
}