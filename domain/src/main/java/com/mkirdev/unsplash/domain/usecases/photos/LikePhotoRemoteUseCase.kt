package com.mkirdev.unsplash.domain.usecases.photos

import com.mkirdev.unsplash.core.contract.usecase.UseCaseWithParam
import com.mkirdev.unsplash.domain.repository.PhotosRepository
import javax.inject.Inject

class LikePhotoRemoteUseCase @Inject constructor(
    private val photosRepository: PhotosRepository
) : UseCaseWithParam<String> {
    override suspend fun execute(id: String) {
        photosRepository.likePhotoRemote(photoId = id)
    }
}