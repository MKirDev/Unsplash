package com.mkirdev.unsplash.domain.repository

import androidx.paging.PagingData
import com.mkirdev.unsplash.domain.models.Collection
import com.mkirdev.unsplash.domain.models.Photo
import kotlinx.coroutines.flow.Flow

interface CollectionsRepository {

    fun getCollections(): Flow<PagingData<Collection>>

    suspend fun getCollectionInfo(id: String): Collection

    fun getCollectionPhotos(id: String): Flow<PagingData<Photo>>

    suspend fun clearCollectionsFromDatabase()

}