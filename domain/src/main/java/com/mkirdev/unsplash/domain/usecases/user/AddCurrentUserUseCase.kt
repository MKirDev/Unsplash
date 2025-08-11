package com.mkirdev.unsplash.domain.usecases.user

import com.mkirdev.unsplash.core.contract.usecase.UseCaseWithoutParamAndResult
import com.mkirdev.unsplash.domain.repository.CurrentUserRepository

class AddCurrentUserUseCase(
    private val currentUserRepository: CurrentUserRepository
) : UseCaseWithoutParamAndResult {
    override suspend fun execute() {
        currentUserRepository.addCurrentUser()
    }
}