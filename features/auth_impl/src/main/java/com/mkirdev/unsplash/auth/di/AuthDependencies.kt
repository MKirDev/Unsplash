package com.mkirdev.unsplash.auth.di

import com.mkirdev.unsplash.domain.repository.AuthRepository

interface AuthDependencies {
    val authRepository: AuthRepository
}