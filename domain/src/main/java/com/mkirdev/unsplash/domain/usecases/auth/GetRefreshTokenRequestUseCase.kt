package com.mkirdev.unsplash.domain.usecases.auth

import com.mkirdev.unsplash.core.contract.usecase.UseCaseWithParamAndResult
import com.mkirdev.unsplash.domain.repository.AuthRepository
import net.openid.appauth.TokenRequest

class GetRefreshTokenRequestUseCase(
    private val repository: AuthRepository
) : UseCaseWithParamAndResult<String, TokenRequest> {
    override suspend fun execute(refreshToken: String): TokenRequest {
        return repository.getRefreshTokenRequest(refreshToken = refreshToken)
    }
}