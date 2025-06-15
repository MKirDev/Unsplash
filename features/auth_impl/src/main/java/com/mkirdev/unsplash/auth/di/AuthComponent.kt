package com.mkirdev.unsplash.auth.di

import com.mkirdev.unsplash.auth.impl.AuthViewModelFactory
import com.mkirdev.unsplash.domain.repository.AuthRepository
import dagger.Component
import net.openid.appauth.AuthorizationService

@Component(modules = [AuthModule::class], dependencies = [AuthDependencies::class])
@AuthScope
internal interface AuthComponent : AuthDependencies {

    override val authRepository: AuthRepository
    override val authService: AuthorizationService
    val authViewModelFactory: AuthViewModelFactory

    @Component.Builder
    interface Builder {
        fun addDependencies(dependencies: AuthDependencies): Builder
        fun build() : AuthComponent
    }
}