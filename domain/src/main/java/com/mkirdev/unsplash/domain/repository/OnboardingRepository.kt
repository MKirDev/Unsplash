package com.mkirdev.unsplash.domain.repository

interface OnboardingRepository {
    suspend fun saveFlag(isOnboardingEnded: Boolean)

    suspend fun getFlag(): Boolean

    suspend fun clear()
}