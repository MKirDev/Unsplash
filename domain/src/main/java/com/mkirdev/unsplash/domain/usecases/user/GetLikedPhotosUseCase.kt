package com.mkirdev.unsplash.domain.usecases.user

import androidx.paging.PagingData
import com.mkirdev.unsplash.core.contract.usecase.UseCaseWithParamAndResult
import com.mkirdev.unsplash.domain.models.Photo
import com.mkirdev.unsplash.domain.repository.CurrentUserRepository
import kotlinx.coroutines.flow.Flow

class GetLikedPhotosUseCase(
    private val currentUserRepository: CurrentUserRepository
) : UseCaseWithParamAndResult<String, Flow<PagingData<Photo>>> {
    override suspend fun execute(username: String): Flow<PagingData<Photo>> {
        return currentUserRepository.getLikedPhotos(username)
    }
}