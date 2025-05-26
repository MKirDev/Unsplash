package com.mkirdev.unsplash.domain.usecases.auth

import com.mkirdev.unsplash.core.contract.usecase.UseCaseWithParams
import com.mkirdev.unsplash.domain.repository.AuthRepository
import net.openid.appauth.AuthorizationService
import net.openid.appauth.TokenRequest

class PerformTokenRequestUseCase(
    private val repository: AuthRepository
) : UseCaseWithParams<AuthorizationService, TokenRequest> {
    override suspend fun execute(authServise: AuthorizationService, tokenRequest: TokenRequest) {
        repository.performTokenRequest(
            authService = authServise,
            tokenRequest = tokenRequest
        )
    }
}