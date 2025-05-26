package com.mkirdev.unsplash.data.storages.datastore.auth

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private const val ACCESS_KEY = "STORAGE_AUTH_ACCESS_KEY"
private const val REFRESH_KEY = "STORAGE_AUTH_REFRESH_KEY"
private const val ID_KEY = "STORAGE_AUTH_ID_KEY"

private const val EMPTY_STRING = ""

class AuthStorage(
    private val dataStore: DataStore<Preferences>
) {
    private val accessKey: Preferences.Key<String> = stringPreferencesKey(ACCESS_KEY)

    private val refreshKey: Preferences.Key<String> = stringPreferencesKey(REFRESH_KEY)

    private val idKey: Preferences.Key<String> = stringPreferencesKey(ID_KEY)

    suspend fun addAccessToken(accessToken: String) {
        dataStore.edit {
            it[accessKey] = accessToken
        }
    }

    suspend fun getAccessToken(): String {
        return dataStore.data.map {
            it[accessKey] ?: EMPTY_STRING
        }.first()
    }

    suspend fun addRefreshToken(refreshToken: String) {
        dataStore.edit {
            it[refreshKey] = refreshToken
        }
    }

    suspend fun getRefreshToken(): String {
        return dataStore.data.map {
            it[refreshKey] ?: EMPTY_STRING
        }.first()
    }

    suspend fun addIdToken(idToken: String) {
        dataStore.edit {
            it[idKey] = idToken
        }
    }

    suspend fun getIdToken(): String {
        return dataStore.data.map {
            it[idKey] ?: EMPTY_STRING
        }.first()
    }

    suspend fun clear() {
        dataStore.edit {
            it.clear()
        }
    }
}