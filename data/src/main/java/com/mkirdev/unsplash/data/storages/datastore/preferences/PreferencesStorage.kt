package com.mkirdev.unsplash.data.storages.datastore.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val SCHEDULE_KEY = "STORAGE_SCHEDULER_KEY"

class PreferencesStorage(
    private val dataStore: DataStore<Preferences>
) {
    private val scheduleKey: Preferences.Key<Boolean> = booleanPreferencesKey(SCHEDULE_KEY)

    suspend fun saveScheduleFlag(isScheduleStarted: Boolean) {
        dataStore.edit {
            it[scheduleKey] = isScheduleStarted
        }
    }

    fun readScheduleFlag(): Flow<Boolean?> {
        return dataStore.data.map { it[scheduleKey] }
    }

    suspend fun removeScheduleFlag() {
        dataStore.edit {
            it.remove(scheduleKey)
        }
    }
}