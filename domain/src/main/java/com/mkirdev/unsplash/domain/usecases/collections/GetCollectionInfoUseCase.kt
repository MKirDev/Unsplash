package com.mkirdev.unsplash.domain.usecases.collections

import com.mkirdev.unsplash.core.contract.usecase.UseCaseWithParamAndResult
import com.mkirdev.unsplash.domain.models.Collection
import com.mkirdev.unsplash.domain.repository.CollectionsRepository

class GetCollectionInfoUseCase(
    private val collectionsRepository: CollectionsRepository
) : UseCaseWithParamAndResult<String, Collection> {
    override suspend fun execute(collectionId: String): Collection {
        return collectionsRepository.getCollectionInfo(collectionId)
    }
}