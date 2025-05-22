package com.mkirdev.unsplash.domain.usecases.onboarding

import com.mkirdev.unsplash.core.contract.usecase.UseCaseWithoutParamAndResult
import com.mkirdev.unsplash.domain.repository.OnboardingRepository
import javax.inject.Inject

class ClearOnboardingFlagUseCase @Inject constructor(
    private val onboardingRepository: OnboardingRepository
) : UseCaseWithoutParamAndResult {
    override suspend fun execute() {
        onboardingRepository.clear()
    }
}