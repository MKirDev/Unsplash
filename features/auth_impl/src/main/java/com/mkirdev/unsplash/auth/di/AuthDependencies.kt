package com.mkirdev.unsplash.auth.di

import com.mkirdev.unsplash.domain.repository.AuthRepository
import net.openid.appauth.AuthorizationService

interface AuthDependencies {
    val authRepository: AuthRepository
    val authService: AuthorizationService
}