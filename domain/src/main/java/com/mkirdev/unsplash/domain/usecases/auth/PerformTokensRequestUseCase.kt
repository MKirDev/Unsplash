package com.mkirdev.unsplash.domain.usecases.auth

import com.mkirdev.unsplash.core.contract.usecase.UseCaseWithParam
import com.mkirdev.unsplash.domain.repository.AuthRepository
import javax.inject.Inject

class PerformTokensRequestUseCase @Inject constructor(
    private val repository: AuthRepository
) : UseCaseWithParam<String> {
    override suspend fun execute(tokenRequestJson: String) {
        repository.performTokensRequest(
            tokenRequestJson = tokenRequestJson
        )
    }
}