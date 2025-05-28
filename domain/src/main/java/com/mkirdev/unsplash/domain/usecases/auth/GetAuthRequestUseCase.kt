package com.mkirdev.unsplash.domain.usecases.auth

import com.mkirdev.unsplash.core.contract.usecase.UseCaseWithResult
import com.mkirdev.unsplash.domain.repository.AuthRepository
import javax.inject.Inject

class GetAuthRequestUseCase @Inject constructor(
    private val repository: AuthRepository
) : UseCaseWithResult<String> {
    override suspend fun execute(): String {
        return repository.getAuthRequest()
    }
}