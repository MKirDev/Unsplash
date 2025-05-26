package com.mkirdev.unsplash.domain.repository

import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationService
import net.openid.appauth.TokenRequest

interface AuthRepository {
    suspend fun getAuthRequest(): AuthorizationRequest

    suspend fun getRefreshTokenRequest(refreshToken: String): TokenRequest

    suspend fun performTokenRequest(
        authService: AuthorizationService,
        tokenRequest: TokenRequest
    )

    suspend fun getSavedToken(): String

    suspend fun clear()
}