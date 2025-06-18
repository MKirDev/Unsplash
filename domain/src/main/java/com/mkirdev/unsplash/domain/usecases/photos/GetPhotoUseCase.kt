package com.mkirdev.unsplash.domain.usecases.photos

import com.mkirdev.unsplash.core.contract.usecase.UseCaseWithParamAndResult
import com.mkirdev.unsplash.domain.models.Photo
import com.mkirdev.unsplash.domain.repository.PhotosRepository
import javax.inject.Inject

class GetPhotoUseCase @Inject constructor(
    private val photosRepository: PhotosRepository
) : UseCaseWithParamAndResult<String, Photo> {
    override suspend fun execute(id: String): Photo {
        return photosRepository.getPhoto(id)
    }
}