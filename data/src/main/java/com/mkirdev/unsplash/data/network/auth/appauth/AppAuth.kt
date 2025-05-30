package com.mkirdev.unsplash.data.network.auth.appauth

import android.net.Uri
import androidx.core.net.toUri
import com.mkirdev.unsplash.data.BuildConfig
import com.mkirdev.unsplash.data.network.auth.models.TokensNetwork
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationService
import net.openid.appauth.AuthorizationServiceConfiguration
import net.openid.appauth.ClientAuthentication
import net.openid.appauth.ClientSecretPost
import net.openid.appauth.GrantTypeValues
import net.openid.appauth.ResponseTypeValues
import net.openid.appauth.TokenRequest
import kotlin.coroutines.suspendCoroutine

class AppAuth {

    private val authRequest = MutableStateFlow<AuthorizationRequest?>(null)

    private val serviceConfiguration = AuthorizationServiceConfiguration(
        Uri.parse(BuildConfig.UNSPLASH_AUTH_BASE_URI),
        Uri.parse(BuildConfig.TOKEN_URI),
        null,
        null
    )

    @Synchronized
    fun getAuthRequest(): AuthorizationRequest {
        val redirectUri = BuildConfig.REDIRECT_URI.toUri()
        val authorizationRequest = AuthorizationRequest.Builder(
            serviceConfiguration,
            BuildConfig.CLIENT_ID,
            ResponseTypeValues.CODE,
            redirectUri
        )
            .setScope(BuildConfig.SCOPE)
            .build()
        authRequest.update { authorizationRequest }
        return authorizationRequest
    }

    @Synchronized
    fun getRefreshTokenRequest(refreshToken: String): TokenRequest {
        return TokenRequest.Builder(
            serviceConfiguration,
            BuildConfig.CLIENT_ID
        )
            .setGrantType(GrantTypeValues.REFRESH_TOKEN)
            .setScopes(BuildConfig.SCOPE)
            .setRefreshToken(refreshToken)
            .build()
    }

    suspend fun performTokenRequestSuspend(
        authService: AuthorizationService,
        authCode: String
    ): TokensNetwork {
        val tokenRequest = authRequest.value?.let {
            TokenRequest.Builder(
                it.configuration,
                it.clientId
            )
                .setGrantType(GrantTypeValues.AUTHORIZATION_CODE)
                .setRedirectUri(it.redirectUri)
                .setCodeVerifier(it.codeVerifier)
                .setAuthorizationCode(authCode)
                .setAdditionalParameters(emptyMap())
                .setNonce(it.nonce)
                .build()
        }
        return suspendCoroutine { continuation ->
            tokenRequest?.let {
                authService.performTokenRequest(it, getClientAuthentication()) { response, ex ->
                    when {
                        response != null -> {
                            authRequest.update { null }
                            val tokens = TokensNetwork(
                                accessToken = response.accessToken.orEmpty(),
                                refreshToken = response.refreshToken.orEmpty(),
                                idToken = response.idToken.orEmpty()
                            )
                            continuation.resumeWith(Result.success(tokens))
                        }
                    }
                }
            }
        }
    }

    @Synchronized
    private fun getClientAuthentication(): ClientAuthentication {
        return ClientSecretPost(BuildConfig.CLIENT_SECRET)
    }


}