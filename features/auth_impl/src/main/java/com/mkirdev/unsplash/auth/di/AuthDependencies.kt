package com.mkirdev.unsplash.auth.di

import com.mkirdev.unsplash.domain.repository.AuthRepository
import com.mkirdev.unsplash.domain.usecases.preferences.DeleteScheduleFlagUseCase
import com.mkirdev.unsplash.domain.usecases.preferences.SaveScheduleFlagUseCase
import com.mkirdev.unsplash.domain.usecases.user.AddCurrentUserUseCase
import net.openid.appauth.AuthorizationService

interface AuthDependencies {
    val authRepository: AuthRepository
    val authService: AuthorizationService

    val addCurrentUserUseCase: AddCurrentUserUseCase

    val deleteScheduleFlagUseCase: DeleteScheduleFlagUseCase
}