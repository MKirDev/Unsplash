package com.mkirdev.unsplash.domain.usecases.photos

import com.mkirdev.unsplash.core.contract.usecase.UseCaseWithParam
import com.mkirdev.unsplash.domain.repository.PhotosRepository
import javax.inject.Inject

class UnlikePhotoLocalUseCase(
    private val photosRepository: PhotosRepository
) : UseCaseWithParam<String> {
    override suspend fun execute(id: String) {
        return photosRepository.unlikePhotoLocal(photoId = id)
    }
}