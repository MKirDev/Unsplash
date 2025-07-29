package com.mkirdev.unsplash.domain.usecases.collections

import androidx.paging.PagingData
import com.mkirdev.unsplash.core.contract.usecase.UseCaseWithParamAndResult
import com.mkirdev.unsplash.domain.models.Photo
import com.mkirdev.unsplash.domain.repository.CollectionsRepository
import kotlinx.coroutines.flow.Flow

class GetCollectionPhotosUseCase(
    private val collectionsRepository: CollectionsRepository
) : UseCaseWithParamAndResult<String, Flow<PagingData<Photo>>> {
    override suspend fun execute(collectionId: String): Flow<PagingData<Photo>> {
        return collectionsRepository.getCollectionPhotos(collectionId)
    }
}