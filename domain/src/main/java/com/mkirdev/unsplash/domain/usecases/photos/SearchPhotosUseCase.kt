package com.mkirdev.unsplash.domain.usecases.photos

import androidx.paging.PagingData
import com.mkirdev.unsplash.core.contract.usecase.UseCaseWithParamAndResult
import com.mkirdev.unsplash.domain.models.Photo
import com.mkirdev.unsplash.domain.repository.PhotosRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchPhotosUseCase @Inject constructor(
    private val photosRepository: PhotosRepository
) :
    UseCaseWithParamAndResult<String, Flow<PagingData<Photo>>> {
    override suspend fun execute(query: String): Flow<PagingData<Photo>> {
        return photosRepository.searchPhotos(query = query)
    }
}