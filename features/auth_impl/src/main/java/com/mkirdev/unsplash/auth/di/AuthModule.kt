package com.mkirdev.unsplash.auth.di

import com.mkirdev.unsplash.auth.impl.AuthViewModelFactory
import com.mkirdev.unsplash.domain.usecases.auth.GetAuthRequestUseCase
import com.mkirdev.unsplash.domain.usecases.auth.GetSavedTokenUseCase
import com.mkirdev.unsplash.domain.usecases.auth.PerformTokensRequestUseCase
import dagger.Module
import dagger.Provides

@Module
internal class AuthModule {
    @Provides
    fun providesAuthViewModelFactory(
        getAuthRequestUseCase: GetAuthRequestUseCase,
        getSavedTokenUseCase: GetSavedTokenUseCase,
        performTokensRequestUseCase: PerformTokensRequestUseCase
    ): AuthViewModelFactory {
        return AuthViewModelFactory(
            getAuthRequestUseCase = getAuthRequestUseCase,
            getSavedTokenUseCase = getSavedTokenUseCase,
            performTokensRequestUseCase = performTokensRequestUseCase
        )
    }
}