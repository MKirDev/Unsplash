package com.mkirdev.unsplash.domain.usecases.photos

import com.mkirdev.unsplash.core.contract.usecase.UseCaseWithoutParamAndResult
import com.mkirdev.unsplash.domain.repository.PhotosRepository

class ClearPhotosDatabaseUseCase(
    private val photosRepository: PhotosRepository
) : UseCaseWithoutParamAndResult {
    override suspend fun execute() {
        photosRepository.clearPhotosFromDatabase()
    }
}