package com.mkirdev.unsplash.domain.repository

import kotlinx.coroutines.flow.SharedFlow

interface AuthRepository {
    suspend fun getAuthRequest(): String

    suspend fun performTokensRequest(
        authCode: String
    )

    suspend fun getSavedToken(): String

    suspend fun getLogoutEvent(): SharedFlow<Unit>

    suspend fun clear()
}