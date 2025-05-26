package com.mkirdev.unsplash.domain.usecases.auth

import com.mkirdev.unsplash.core.contract.usecase.UseCaseWithResult
import com.mkirdev.unsplash.domain.repository.AuthRepository
import net.openid.appauth.AuthorizationRequest

class GetAuthRequestUseCase(
    private val repository: AuthRepository
) : UseCaseWithResult<AuthorizationRequest> {
    override suspend fun execute(): AuthorizationRequest {
        return repository.getAuthRequest()
    }
}