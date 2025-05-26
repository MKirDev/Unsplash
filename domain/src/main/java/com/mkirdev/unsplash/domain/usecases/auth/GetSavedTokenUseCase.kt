package com.mkirdev.unsplash.domain.usecases.auth

import com.mkirdev.unsplash.core.contract.usecase.UseCaseWithResult
import com.mkirdev.unsplash.domain.repository.AuthRepository

class GetSavedTokenUseCase(
    private val repository: AuthRepository
) : UseCaseWithResult<String> {
    override suspend fun execute(): String {
        return repository.getSavedToken()
    }
}