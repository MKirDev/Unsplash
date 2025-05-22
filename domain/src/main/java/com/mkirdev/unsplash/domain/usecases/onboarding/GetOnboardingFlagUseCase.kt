package com.mkirdev.unsplash.domain.usecases.onboarding

import com.mkirdev.unsplash.core.contract.usecase.UseCaseWithResult
import com.mkirdev.unsplash.domain.repository.OnboardingRepository
import javax.inject.Inject

class GetOnboardingFlagUseCase @Inject constructor(
    private val onboardingRepository: OnboardingRepository
) : UseCaseWithResult<Boolean> {
    override suspend fun execute(): Boolean {
        return onboardingRepository.getFlag()
    }
}