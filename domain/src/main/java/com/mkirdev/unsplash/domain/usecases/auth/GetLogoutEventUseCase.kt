package com.mkirdev.unsplash.domain.usecases.auth

import com.mkirdev.unsplash.core.contract.usecase.UseCaseWithResult
import com.mkirdev.unsplash.domain.repository.AuthRepository
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

class GetLogoutEventUseCase @Inject constructor(
    private val authRepository: AuthRepository
) : UseCaseWithResult<SharedFlow<Unit>> {
    override suspend fun execute(): SharedFlow<Unit> {
        return authRepository.getLogoutEvent()
    }
}