package com.mkirdev.unsplash.domain.usecases.onboarding

import com.mkirdev.unsplash.core.contract.usecase.UseCaseWithoutParamAndResult
import com.mkirdev.unsplash.domain.repository.OnboardingRepository

class ClearOnboardingFlagUseCase(
    private val onboardingRepository: OnboardingRepository
) : UseCaseWithoutParamAndResult {
    override suspend fun execute() {
        onboardingRepository.clear()
    }
}