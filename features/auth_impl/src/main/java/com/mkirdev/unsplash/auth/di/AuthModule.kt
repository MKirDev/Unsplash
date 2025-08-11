package com.mkirdev.unsplash.auth.di

import com.mkirdev.unsplash.auth.impl.AuthViewModelFactory
import com.mkirdev.unsplash.domain.usecases.auth.GetAuthRequestUseCase
import com.mkirdev.unsplash.domain.usecases.auth.GetSavedTokenUseCase
import com.mkirdev.unsplash.domain.usecases.auth.PerformTokensRequestUseCase
import com.mkirdev.unsplash.domain.usecases.preferences.DeleteScheduleFlagUseCase
import com.mkirdev.unsplash.domain.usecases.user.AddCurrentUserUseCase
import dagger.Module
import dagger.Provides

@Module
internal class AuthModule {
    @Provides
    fun providesAuthViewModelFactory(
        getAuthRequestUseCase: GetAuthRequestUseCase,
        getSavedTokenUseCase: GetSavedTokenUseCase,
        performTokensRequestUseCase: PerformTokensRequestUseCase,
        addCurrentUserUseCase: AddCurrentUserUseCase,
        deleteScheduleFlagUseCase: DeleteScheduleFlagUseCase
    ): AuthViewModelFactory {
        return AuthViewModelFactory(
            getAuthRequestUseCase = getAuthRequestUseCase,
            getSavedTokenUseCase = getSavedTokenUseCase,
            performTokensRequestUseCase = performTokensRequestUseCase,
            addCurrentUserUseCase = addCurrentUserUseCase,
            deleteScheduleFlagUseCase = deleteScheduleFlagUseCase
        )
    }
}