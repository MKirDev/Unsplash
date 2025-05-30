package com.mkirdev.unsplash.domain.repository

interface AuthRepository {
    suspend fun getAuthRequest(): String

    suspend fun performTokensRequest(
        authCode: String
    )

    suspend fun getSavedToken(): String

    suspend fun clear()
}