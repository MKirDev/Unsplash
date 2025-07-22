package com.mkirdev.unsplash.di

import com.mkirdev.unsplash.data.repository.auth.AuthRepositoryImpl
import com.mkirdev.unsplash.data.repository.onboarding.OnboardingRepositoryImpl
import com.mkirdev.unsplash.data.repository.photos.PhotosRepositoryImpl
import com.mkirdev.unsplash.data.repository.preferences.PreferencesRepositoryImpl
import com.mkirdev.unsplash.domain.repository.AuthRepository
import com.mkirdev.unsplash.domain.repository.OnboardingRepository
import com.mkirdev.unsplash.domain.repository.PhotosRepository
import com.mkirdev.unsplash.domain.repository.PreferencesRepository
import dagger.Binds
import dagger.Module

@Module
interface RepositoriesModule {

    @Binds
    fun bindOnboardingRepository(repository: OnboardingRepositoryImpl): OnboardingRepository

    @Binds
    fun bindAuthRepository(repository: AuthRepositoryImpl): AuthRepository

    @Binds
    fun bindPhotosRepository(repository: PhotosRepositoryImpl): PhotosRepository

    @Binds
    fun bindPreferencesRepository(repository: PreferencesRepositoryImpl): PreferencesRepository
}