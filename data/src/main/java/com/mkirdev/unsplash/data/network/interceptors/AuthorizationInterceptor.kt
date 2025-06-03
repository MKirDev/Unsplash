package com.mkirdev.unsplash.data.network.interceptors

import com.mkirdev.unsplash.data.storages.datastore.auth.AuthStorage
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

private const val AUTHORIZATION_NAME = "Authorization"

class AuthorizationInterceptor(
    private val authStorage: AuthStorage
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.request()
            .addTokenHeader()
            .let { chain.proceed(it) }
    }

    private fun Request.addTokenHeader(): Request {
        val authHeaderName = AUTHORIZATION_NAME
        return newBuilder()
            .apply {
                val token = runBlocking { authStorage.getAccessToken() }
                if (token.isNotEmpty()) {
                    header(authHeaderName, token.withBearer())
                }
            }
            .build()
    }

    private fun String.withBearer() = "Bearer $this"
}