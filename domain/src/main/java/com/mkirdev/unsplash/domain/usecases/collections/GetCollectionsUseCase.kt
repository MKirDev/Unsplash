package com.mkirdev.unsplash.domain.usecases.collections

import androidx.paging.PagingData
import com.mkirdev.unsplash.core.contract.usecase.UseCaseWithResult
import com.mkirdev.unsplash.domain.models.Collection
import com.mkirdev.unsplash.domain.repository.CollectionsRepository
import kotlinx.coroutines.flow.Flow

class GetCollectionsUseCase(
    private val collectionsRepository: CollectionsRepository
) : UseCaseWithResult<Flow<PagingData<Collection>>> {
    override suspend fun execute(): Flow<PagingData<Collection>> {
        return collectionsRepository.getCollections()
    }
}