package com.mkirdev.unsplash.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.room.Room
import com.mkirdev.unsplash.app.DataStoreManager
import com.mkirdev.unsplash.data.storages.database.factory.AppDatabase
import com.mkirdev.unsplash.data.storages.datastore.auth.AuthStorage
import com.mkirdev.unsplash.data.storages.datastore.onboarding.OnboardingStorage
import com.mkirdev.unsplash.data.storages.datastore.photos.PhotosStorage
import com.mkirdev.unsplash.data.storages.datastore.preferences.PreferencesStorage
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

private const val ONBOARDING_DATA_STORE = "onboardingDataStore"
private const val AUTH_DATA_STORE = "authDataStore"

private const val PREFERENCES_DATA_STORE = "preferencesDataStore"

private const val PROJECT_DATABASE = "project_database"

@Module
class StorageModule {

    @Provides
    @Named(ONBOARDING_DATA_STORE)
    fun provideOnboardingDataStore(dataStoreManager: DataStoreManager): DataStore<Preferences> {
        return dataStoreManager.onboardingDataStore
    }

    @Provides
    @Named(AUTH_DATA_STORE)
    fun provideAuthDataStore(dataStoreManager: DataStoreManager): DataStore<Preferences> {
        return dataStoreManager.authDataStore
    }

    @Provides
    @Named(PREFERENCES_DATA_STORE)
    fun providePreferencesDataStore(dataStoreManager: DataStoreManager): DataStore<Preferences> {
       return dataStoreManager.preferencesDataStore
    }

    @Singleton
    @Provides
    fun provideOnboardingStorage(
        @Named(ONBOARDING_DATA_STORE) dataStore: DataStore<Preferences>
    ): OnboardingStorage {
        return OnboardingStorage(dataStore = dataStore)
    }

    @Singleton
    @Provides
    fun provideAuthStorage(
        @Named(AUTH_DATA_STORE) dataStore: DataStore<Preferences>
    ): AuthStorage {
        return AuthStorage(dataStore = dataStore)
    }

    @Singleton
    @Provides
    fun providePhotosStorage(): PhotosStorage {
        return PhotosStorage()
    }

    @Singleton
    @Provides
    fun providePreferencesStorage(
        @Named(PREFERENCES_DATA_STORE) dataStore: DataStore<Preferences>
    ): PreferencesStorage {
        return PreferencesStorage(dataStore = dataStore)
    }

    @Singleton
    @Provides
    fun provideDatabase(
        context: Context
    ) : AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            PROJECT_DATABASE
        ).build()
    }
}