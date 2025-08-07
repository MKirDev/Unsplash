package com.mkirdev.unsplash.domain.usecases.collections

import com.mkirdev.unsplash.core.contract.usecase.UseCaseWithoutParamAndResult
import com.mkirdev.unsplash.domain.repository.CollectionsRepository

class ClearCollectionsDatabaseUseCase(
    private val collectionsRepository: CollectionsRepository
) : UseCaseWithoutParamAndResult {
    override suspend fun execute() {
        collectionsRepository.clearCollectionsFromDatabase()
    }
}