package com.mkirdev.unsplash.data.repository.auth

import com.google.gson.Gson
import com.mkirdev.unsplash.data.exceptions.AuthException
import com.mkirdev.unsplash.data.network.auth.appauth.AppAuth
import com.mkirdev.unsplash.data.storages.datastore.auth.AuthStorage
import com.mkirdev.unsplash.domain.repository.AuthRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.openid.appauth.AuthorizationService
import net.openid.appauth.TokenRequest
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthorizationService,
    private val appAuth: AppAuth,
    private val authStorage: AuthStorage,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : AuthRepository {
    override suspend fun getAuthRequest(): String = withContext(dispatcher) {
        try {
            val authRequest = appAuth.getAuthRequest()
            Gson().toJson(authRequest)
        } catch (throwable: Throwable) {
            throw AuthException.GetAuthRequestException(throwable)
        }
    }

    override suspend fun performTokensRequest(
        tokenRequestJson: String
    ) = withContext(dispatcher) {
        try {
            val tokenRequest = TokenRequest.jsonDeserialize(tokenRequestJson)
            val tokens = appAuth.performTokenRequestSuspend(authService, tokenRequest)
            authStorage.addAccessToken(tokens.accessToken)
            authStorage.addRefreshToken(tokens.refreshToken)
            authStorage.addIdToken(tokens.idToken)
        } catch (throwable: Throwable) {
            throw AuthException.PerformTokenRequestException(throwable)
        }
    }

    override suspend fun getSavedToken(): String = withContext(dispatcher) {
        try {
            authStorage.getAccessToken()
        } catch (throwable: Throwable) {
            throw AuthException.GetSavedTokenRequestException(throwable)
        }
    }

    override suspend fun clear() = withContext(dispatcher) {
        try {
            authStorage.clear()
        } catch (throwable: Throwable) {
            throw AuthException.ClearDataException(throwable)
        }
    }
}