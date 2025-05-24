package com.mkirdev.unsplash.onboarding.di

import com.mkirdev.unsplash.domain.usecases.onboarding.GetOnboardingFlagUseCase
import com.mkirdev.unsplash.domain.usecases.onboarding.SaveOnboardingFlagUseCase
import com.mkirdev.unsplash.onboarding.impl.OnboardingViewModelFactory
import dagger.Module
import dagger.Provides

@Module
internal class OnboardingModule {

    @Provides
    fun provideOnboardingViewModelFactory(
        saveOnboardingFlagUseCase: SaveOnboardingFlagUseCase,
        getOnboardingFlagUseCase: GetOnboardingFlagUseCase
    ): OnboardingViewModelFactory {
        return OnboardingViewModelFactory(
            saveOnboardingFlagUseCase = saveOnboardingFlagUseCase,
            getOnboardingFlagUseCase = getOnboardingFlagUseCase
        )
    }

}