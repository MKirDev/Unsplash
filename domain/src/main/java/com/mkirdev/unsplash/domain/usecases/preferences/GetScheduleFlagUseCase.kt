package com.mkirdev.unsplash.domain.usecases.preferences

import com.mkirdev.unsplash.core.contract.usecase.UseCaseWithResult
import com.mkirdev.unsplash.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow

class GetScheduleFlagUseCase(
    private val repository: PreferencesRepository
) : UseCaseWithResult<Flow<Boolean?>> {
    override suspend fun execute(): Flow<Boolean?> {
        return repository.getScheduleFlag()
    }
}