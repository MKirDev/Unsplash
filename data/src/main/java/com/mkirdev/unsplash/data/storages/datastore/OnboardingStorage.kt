package com.mkirdev.unsplash.data.storages.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private const val KEY = "STORAGE_ONBOARDING_KEY"
class OnboardingStorage(
    private val dataStore: DataStore<Preferences>
) {
    private val key: Preferences.Key<Boolean> = booleanPreferencesKey(KEY)

    suspend fun addFlag(isOnboardingEnded: Boolean) {
        dataStore.edit {
            it[key] = isOnboardingEnded
        }
    }

    suspend fun getFlag(): Boolean {
        return dataStore.data.map {
            it[key] ?: false
        }.first()
    }

    suspend fun clear() {
        dataStore.edit {
            it.clear()
        }
    }
}