package com.mkirdev.unsplash.data.storages.datastore.photos

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val LIKE_KEY = "STORAGE_PHOTOS_LIKE_KEY"
private const val UNLIKE_KEY = "STORAGE_PHOTOS_UNLIKE_KEY"
private const val DOWNLOAD_KEY = "STORAGE_PHOTOS_DOWNLOAD_KEY"

private const val EMPTY_STRING = ""
class PhotosStorage(
    private val dataStore: DataStore<Preferences>
) {
    private val likeKey: Preferences.Key<String> = stringPreferencesKey(LIKE_KEY)

    private val unlikeKey: Preferences.Key<String> = stringPreferencesKey(UNLIKE_KEY)

    private val downloadKey: Preferences.Key<String> = stringPreferencesKey(DOWNLOAD_KEY)

    suspend fun addLikedPhoto(id: String) {
        dataStore.edit {
            it[likeKey] = id
        }
    }

    fun getLikedPhoto(): Flow<String> {
        return dataStore.data.map {
            it[likeKey] ?: EMPTY_STRING
        }
    }

    suspend fun addUnlikedPhoto(id: String) {
        dataStore.edit {
            it[unlikeKey] = id
        }
    }

    fun getUnlikedPhoto(): Flow<String> {
        return dataStore.data.map {
            it[unlikeKey] ?: EMPTY_STRING
        }
    }

    suspend fun addDownloadLink(link: String) {
        dataStore.edit {
            it[downloadKey] = link
        }
    }

    fun getDownloadLink(): Flow<String> {
        return dataStore.data.map {
            it[downloadKey] ?: EMPTY_STRING
        }
    }

    suspend fun clear() {
        dataStore.edit {
            it.clear()
        }
    }

}