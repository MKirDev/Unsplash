package com.mkirdev.unsplash.domain.usecases.user

import com.mkirdev.unsplash.core.contract.usecase.UseCaseWithResult
import com.mkirdev.unsplash.domain.models.CurrentUser
import com.mkirdev.unsplash.domain.repository.CurrentUserRepository

class GetUserInfoUseCase(
    private val currentUserRepository: CurrentUserRepository
) : UseCaseWithResult<CurrentUser> {
    override suspend fun execute(): CurrentUser {
        return currentUserRepository.getUserInfo()
    }
}