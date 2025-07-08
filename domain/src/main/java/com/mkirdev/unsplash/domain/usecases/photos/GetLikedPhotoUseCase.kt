package com.mkirdev.unsplash.domain.usecases.photos

import com.mkirdev.unsplash.core.contract.usecase.UseCaseWithResult
import com.mkirdev.unsplash.domain.repository.PhotosRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLikedPhotoUseCase(
    private val photosRepository: PhotosRepository
) : UseCaseWithResult<Flow<String>> {
    override suspend fun execute(): Flow<String> {
        return photosRepository.getLikedPhoto()
    }
}