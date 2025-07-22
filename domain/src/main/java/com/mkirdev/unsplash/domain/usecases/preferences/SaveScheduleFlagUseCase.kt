package com.mkirdev.unsplash.domain.usecases.preferences

import com.mkirdev.unsplash.core.contract.usecase.UseCaseWithParam
import com.mkirdev.unsplash.domain.repository.PreferencesRepository

class SaveScheduleFlagUseCase(
    private val repository: PreferencesRepository
) : UseCaseWithParam<Boolean> {
    override suspend fun execute(isStarted: Boolean) {
        repository.saveScheduleFlag(isStarted)
    }
}