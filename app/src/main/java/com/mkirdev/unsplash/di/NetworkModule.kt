package com.mkirdev.unsplash.di

import android.content.Context
import com.mkirdev.unsplash.BuildConfig
import com.mkirdev.unsplash.data.network.auth.appauth.AppAuth
import com.mkirdev.unsplash.data.network.interceptors.AuthorizationFailedInterceptor
import com.mkirdev.unsplash.data.network.interceptors.AuthorizationInterceptor
import com.mkirdev.unsplash.data.network.managers.AuthStateManager
import com.mkirdev.unsplash.data.storages.datastore.auth.AuthStorage
import dagger.Module
import dagger.Provides
import net.openid.appauth.AuthorizationService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    fun provideAuthService(context: Context): AuthorizationService {
        return AuthorizationService(context.applicationContext)
    }

    @Singleton
    @Provides
    fun provideAppAuth(): AppAuth {
        return AppAuth()
    }

    @Singleton
    @Provides
    fun provideAuthStateManager(): AuthStateManager {
        return AuthStateManager()
    }

    @Provides
    fun provideAuthorizationInterceptor(authStorage: AuthStorage): AuthorizationInterceptor {
        return AuthorizationInterceptor(authStorage = authStorage)
    }

    @Provides
    fun provideAuthorizationFailedInterceptor(
        authorizationService: AuthorizationService,
        authStorage: AuthStorage,
        appAuth: AppAuth,
        authStateManager: AuthStateManager,
    ): AuthorizationFailedInterceptor {
        return AuthorizationFailedInterceptor(
            authorizationService = authorizationService,
            authStorage = authStorage,
            appAuth = appAuth,
            authStateManager = authStateManager
        )
    }

    @Provides
    fun provideOkHttpClient(
        authorizationInterceptor: AuthorizationInterceptor,
        authorizationFailedInterceptor: AuthorizationFailedInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(authorizationInterceptor)
            .addNetworkInterceptor(authorizationFailedInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.UNSPLASH_AUTH_BASE_URI)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


}