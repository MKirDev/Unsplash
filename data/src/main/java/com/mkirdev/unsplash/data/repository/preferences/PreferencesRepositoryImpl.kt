package com.mkirdev.unsplash.data.repository.preferences

import com.mkirdev.unsplash.data.exceptions.PreferencesException
import com.mkirdev.unsplash.data.storages.datastore.preferences.PreferencesStorage
import com.mkirdev.unsplash.domain.repository.PreferencesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PreferencesRepositoryImpl @Inject constructor(
    private val preferencesStorage: PreferencesStorage,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : PreferencesRepository {

    override suspend fun saveScheduleFlag(isStarted: Boolean) = withContext(dispatcher) {
        try {
            preferencesStorage.saveScheduleFlag(isStarted)
        } catch (t: Throwable) {
            throw PreferencesException.SaveScheduleFlagException(t)
        }
    }

    override suspend fun getScheduleFlag(): Flow<Boolean?> = withContext(dispatcher) {
        preferencesStorage.readScheduleFlag().flowOn(dispatcher).catch {
            throw PreferencesException.GetScheduleFlagException(it)
        }
    }

    override suspend fun deleteScheduleFlag() = withContext(dispatcher) {
        try {
            preferencesStorage.removeScheduleFlag()
        } catch (t: Throwable) {
            throw PreferencesException.DeleteScheduleFlagException(t)
        }
    }
}