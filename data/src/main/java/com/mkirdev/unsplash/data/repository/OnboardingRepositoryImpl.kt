package com.mkirdev.unsplash.data.repository

import com.mkirdev.unsplash.data.exceptions.OnboardingException
import com.mkirdev.unsplash.data.storages.datastore.OnboardingStorage
import com.mkirdev.unsplash.domain.repository.OnboardingRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class OnboardingRepositoryImpl(
    private val onboardingStorage: OnboardingStorage,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : OnboardingRepository {
    override suspend fun saveFlag(isOnboardingEnded: Boolean) {
        try {
            withContext(dispatcher) {
                onboardingStorage.addFlag(isOnboardingEnded)
            }
        } catch (throwable: Throwable) {
            throw OnboardingException.SaveFlagException(throwable)
        }
    }

    override suspend fun getFlag(): Boolean {
        return try {
            withContext(dispatcher) {
                onboardingStorage.getFlag()
            }
        } catch (throwable: Throwable) {
           throw OnboardingException.GetFlagException(throwable)
        }
    }

    override suspend fun clear() {
        try {
            withContext(dispatcher) {
                onboardingStorage.clear()
            }
        } catch (throwable: Throwable) {
            throw OnboardingException.ClearDataException(throwable)
        }
    }
}