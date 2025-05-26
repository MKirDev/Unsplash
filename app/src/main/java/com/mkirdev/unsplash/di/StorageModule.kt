package com.mkirdev.unsplash.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.mkirdev.unsplash.app.DataStoreManager
import com.mkirdev.unsplash.data.storages.datastore.onboarding.OnboardingStorage
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

private const val ONBOARDING_STORAGE = "onboardingStorage"

@Module
class StorageModule {

    @Provides
    @Named(ONBOARDING_STORAGE)
    fun provideOnboardingDataStore(dataStoreManager: DataStoreManager): DataStore<Preferences> {
        return dataStoreManager.onboardingDataStore
    }

    @Singleton
    @Provides
    fun provideOnboardingStorage(
        @Named(ONBOARDING_STORAGE) dataStore: DataStore<Preferences>
    ): OnboardingStorage {
        return OnboardingStorage(dataStore = dataStore)
    }
}