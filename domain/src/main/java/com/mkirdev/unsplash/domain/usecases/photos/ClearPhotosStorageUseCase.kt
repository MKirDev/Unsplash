package com.mkirdev.unsplash.domain.usecases.photos

import com.mkirdev.unsplash.core.contract.usecase.UseCaseWithoutParamAndResult
import com.mkirdev.unsplash.domain.repository.PhotosRepository
import javax.inject.Inject

class ClearPhotosStorageUseCase(
    private val photosRepository: PhotosRepository
) : UseCaseWithoutParamAndResult {
    override suspend fun execute() {
        photosRepository.clearPhotosStorage()
    }
}