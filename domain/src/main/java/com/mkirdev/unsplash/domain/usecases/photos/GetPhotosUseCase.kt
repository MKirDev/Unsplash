package com.mkirdev.unsplash.domain.usecases.photos

import androidx.paging.PagingData
import com.mkirdev.unsplash.core.contract.usecase.UseCaseWithResult
import com.mkirdev.unsplash.domain.models.Photo
import com.mkirdev.unsplash.domain.repository.PhotosRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPhotosUseCase(
    private val photosRepository: PhotosRepository
) : UseCaseWithResult<Flow<PagingData<Photo>>> {
    override suspend fun execute(): Flow<PagingData<Photo>> {
        return photosRepository.getPhotos()
    }
}