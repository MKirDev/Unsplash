package com.mkirdev.unsplash.di

import android.content.Context
import com.mkirdev.unsplash.BuildConfig
import com.mkirdev.unsplash.data.network.auth.appauth.AppAuth
import com.mkirdev.unsplash.data.network.collections.api.CollectionsApi
import com.mkirdev.unsplash.data.network.interceptors.AuthorizationFailedInterceptor
import com.mkirdev.unsplash.data.network.interceptors.AuthorizationInterceptor
import com.mkirdev.unsplash.data.network.managers.AuthStateManager
import com.mkirdev.unsplash.data.network.photos.api.DownloadApi
import com.mkirdev.unsplash.data.network.photos.api.PhotosApi
import com.mkirdev.unsplash.data.network.photos.api.SearchApi
import com.mkirdev.unsplash.data.network.photos.api.CurrentUserApi
import com.mkirdev.unsplash.data.storages.datastore.auth.AuthStorage
import dagger.Module
import dagger.Provides
import net.openid.appauth.AuthorizationService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
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
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().also {
            it.level = HttpLoggingInterceptor.Level.BODY
        }
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
        httpLoggingInterceptor: HttpLoggingInterceptor,
        authorizationInterceptor: AuthorizationInterceptor,
        authorizationFailedInterceptor: AuthorizationFailedInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(
                httpLoggingInterceptor
            )
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
            .baseUrl(BuildConfig.UNSPLASH_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun providePhotosApi(
        retrofit: Retrofit
    ): PhotosApi {
        return retrofit.create(PhotosApi::class.java)
    }

    @Singleton
    @Provides
    fun provideSearchApi(
        retrofit: Retrofit
    ): SearchApi {
        return retrofit.create(SearchApi::class.java)
    }

    @Singleton
    @Provides
    fun provideDownloadApi(
        retrofit: Retrofit
    ): DownloadApi {
        return retrofit.create(DownloadApi::class.java)
    }

    @Singleton
    @Provides
    fun provideCollectionsApi(
        retrofit: Retrofit
    ): CollectionsApi {
        return retrofit.create(CollectionsApi::class.java)
    }

    @Singleton
    @Provides
    fun provideUsersApi(
        retrofit: Retrofit
    ): CurrentUserApi {
        return retrofit.create(CurrentUserApi::class.java)
    }


}