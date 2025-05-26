package com.mkirdev.unsplash.data.network.auth.appauth

import android.net.Uri
import androidx.core.net.toUri
import com.mkirdev.unsplash.data.BuildConfig
import com.mkirdev.unsplash.data.network.auth.models.TokensNetwork
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

    private val serviceConfiguration = AuthorizationServiceConfiguration(
        Uri.parse(BuildConfig.UNSPLASH_AUTH_BASE_URI),
        Uri.parse(BuildConfig.TOKEN_URI),
        null,
        null
    )

    @Synchronized
    fun getAuthRequest(): AuthorizationRequest {
        val redirectUri = BuildConfig.REDIRECT_URI.toUri()
        return AuthorizationRequest.Builder(
            serviceConfiguration,
            BuildConfig.CLIENT_ID,
            ResponseTypeValues.CODE,
            redirectUri
        )
            .setScope(BuildConfig.SCOPE)
            .build()
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
        tokenRequest: TokenRequest
    ): TokensNetwork {
        return suspendCoroutine { continuation ->
            authService.performTokenRequest(tokenRequest, getClientAuthentication()) { response, ex ->
                when {
                    response != null -> {
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

    @Synchronized
    private fun getClientAuthentication(): ClientAuthentication {
        return ClientSecretPost(BuildConfig.CLIENT_SECRET)
    }


}