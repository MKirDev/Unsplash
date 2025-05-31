package com.mkirdev.unsplash.auth.di

import com.mkirdev.unsplash.domain.repository.AuthRepository
import com.mkirdev.unsplash.domain.usecases.auth.GetAuthRequestUseCase
import com.mkirdev.unsplash.domain.usecases.auth.GetSavedTokenUseCase
import com.mkirdev.unsplash.domain.usecases.auth.PerformTokensRequestUseCase
import dagger.Component
import net.openid.appauth.AuthorizationService

@Component(modules = [AuthModule::class], dependencies = [AuthDependencies::class])
@AuthScope
internal interface AuthComponent : AuthDependencies {

    override val authRepository: AuthRepository
    override val authService: AuthorizationService
    val getAuthRequestUseCase: GetAuthRequestUseCase
    val getSavedTokenUseCase: GetSavedTokenUseCase
    val performTokensRequestUseCase: PerformTokensRequestUseCase

    @Component.Builder
    interface Builder {
        fun addDependencies(dependencies: AuthDependencies): Builder
        fun build() : AuthComponent
    }
}