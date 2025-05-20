package com.mkirdev.unsplash.domain.usecases.onboarding

import com.mkirdev.unsplash.core.contract.usecase.UseCaseWithParam
import com.mkirdev.unsplash.domain.repository.OnboardingRepository

class SaveOnboardingFlagUseCase(
    private val onboardingRepository: OnboardingRepository
) : UseCaseWithParam<Boolean>{
    override suspend fun execute(isOnboardingEnded: Boolean) {
        onboardingRepository.saveFlag(isOnboardingEnded = isOnboardingEnded)
    }
}