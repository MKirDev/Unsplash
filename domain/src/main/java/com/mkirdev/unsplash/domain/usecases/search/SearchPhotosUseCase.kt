package com.mkirdev.unsplash.domain.usecases.search

import androidx.paging.PagingData
import com.mkirdev.unsplash.core.contract.usecase.UseCaseWithParamAndResult
import com.mkirdev.unsplash.domain.models.Photo
import com.mkirdev.unsplash.domain.repository.PhotosRepository
import com.mkirdev.unsplash.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow

class SearchPhotosUseCase(
    private val searchRepository: SearchRepository
) :
    UseCaseWithParamAndResult<String, Flow<PagingData<Photo>>> {
    override suspend fun execute(query: String): Flow<PagingData<Photo>> {
        return searchRepository.searchPhotos(query = query)
    }
}