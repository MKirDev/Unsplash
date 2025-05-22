package com.mkirdev.unsplash.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.mkirdev.unsplash.data.storages.datastore.OnboardingStorage
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class StorageModule {
    @Singleton
    @Provides
    fun provideOnboardingStorage(dataStore: DataStore<Preferences>): OnboardingStorage {
        return OnboardingStorage(dataStore = dataStore)
    }
}