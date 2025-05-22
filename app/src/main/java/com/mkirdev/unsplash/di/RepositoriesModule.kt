package com.mkirdev.unsplash.di

import com.mkirdev.unsplash.data.repository.OnboardingRepositoryImpl
import com.mkirdev.unsplash.domain.repository.OnboardingRepository
import dagger.Binds
import dagger.Module

@Module
interface RepositoriesModule {

    @Binds
    fun bindOnboardingRepository(repository: OnboardingRepositoryImpl): OnboardingRepository
}