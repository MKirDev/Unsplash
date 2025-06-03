package com.mkirdev.unsplash.data.network.interceptors

import com.mkirdev.unsplash.data.network.auth.appauth.AppAuth
import com.mkirdev.unsplash.data.network.managers.AuthStateManager
import com.mkirdev.unsplash.data.storages.datastore.auth.AuthStorage
import kotlinx.coroutines.runBlocking
import net.openid.appauth.AuthorizationService
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class AuthorizationFailedInterceptor(
    private val authorizationService: AuthorizationService,
    private val authStorage: AuthStorage,
    private val appAuth: AppAuth,
    private val authStateManager: AuthStateManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequestTimestamp = System.currentTimeMillis()
        val originalResponse = chain.proceed(chain.request())
        return originalResponse
            .takeIf { it.code != 401 }
            ?: handleUnauthorizedResponse(chain, originalResponse, originalRequestTimestamp)
    }

    private fun handleUnauthorizedResponse(
        chain: Interceptor.Chain,
        originalResponse: Response,
        requestTimestamp: Long
    ) : Response {
        val latch = getLatch()
        return when {
            latch != null && latch.count > 0 -> handleTokenIsUpdating(chain, latch, requestTimestamp)
                ?: originalResponse
            tokenUpdateTime > requestTimestamp -> updateTokenAndProceedChain(chain)
            else -> handleTokenNeedRefresh(chain) ?: originalResponse
        }
    }

    private fun handleTokenIsUpdating(
        chain: Interceptor.Chain,
        latch: CountDownLatch,
        requestTimestamp: Long
    ): Response? {
        return if (latch.await(REQUEST_TIMEOUT, TimeUnit.SECONDS)
            && tokenUpdateTime > requestTimestamp) {
            updateTokenAndProceedChain(chain)
        } else {
            null
        }
    }

    private fun handleTokenNeedRefresh(
        chain: Interceptor.Chain
    ): Response? {
        return if (refreshToken()) {
            updateTokenAndProceedChain(chain)
        } else {
            null
        }
    }

    private fun updateTokenAndProceedChain(
        chain: Interceptor.Chain
    ): Response {
        val newRequest = updateOriginalCallWithNewToken(chain.request())
        return chain.proceed(newRequest)
    }

    private fun refreshToken(): Boolean {
        initLatch()

        val tokenRefreshed = runBlocking {
            kotlin.runCatching {
                val refreshRequest = appAuth.getRefreshTokenRequest(authStorage.getRefreshToken())
                appAuth.performRefreshTokenRequestSuspend(
                    authService = authorizationService,
                    refreshTokenRequest = refreshRequest)
            }
                .getOrNull()
                ?.let { tokens ->
                    authStorage.addAccessToken(tokens.accessToken)
                    authStorage.addRefreshToken(tokens.refreshToken)
                    authStorage.addIdToken(tokens.idToken)
                    true
                } ?: false
        }

        if (tokenRefreshed) {
            tokenUpdateTime = System.currentTimeMillis()
        } else {
            runBlocking { authStateManager.emitLogout() }
        }
        getLatch()?.countDown()

        return tokenRefreshed
    }

    private fun updateOriginalCallWithNewToken(request: Request): Request {
        val newAccessToken = runBlocking { authStorage.getAccessToken() }
        return if (newAccessToken.isNotEmpty()) {
            newAccessToken.let {
                request
                    .newBuilder()
                    .header("Authorization", it)
                    .build()
            }
        } else request
    }



    companion object {
        private const val REQUEST_TIMEOUT = 30L

        @Volatile
        private var tokenUpdateTime: Long = 0L

        private var countDownLatch: CountDownLatch? = null

        @Synchronized
        fun initLatch() {
            countDownLatch = CountDownLatch(1)
        }

        @Synchronized
        fun getLatch() = countDownLatch
    }

}