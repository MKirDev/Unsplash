package com.mkirdev.unsplash.domain.usecases.auth

import com.mkirdev.unsplash.core.contract.usecase.UseCaseWithoutParamAndResult
import com.mkirdev.unsplash.domain.repository.AuthRepository

class ClearAuthTokenUseCase(
    private val repository: AuthRepository
) : UseCaseWithoutParamAndResult {
    override suspend fun execute() {
        repository.clear()
    }
}