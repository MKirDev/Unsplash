package com.mkirdev.unsplash.domain.usecases.preferences

import com.mkirdev.unsplash.core.contract.usecase.UseCaseWithoutParamAndResult
import com.mkirdev.unsplash.domain.repository.PreferencesRepository

class DeleteScheduleFlagUseCase(
    private val repository: PreferencesRepository
) : UseCaseWithoutParamAndResult {
    override suspend fun execute() {
        repository.deleteScheduleFlag()
    }
}