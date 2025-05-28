package com.mkirdev.unsplash.di

import android.content.Context
import com.mkirdev.unsplash.data.network.auth.appauth.AppAuth
import dagger.Module
import dagger.Provides
import net.openid.appauth.AuthorizationService
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


}