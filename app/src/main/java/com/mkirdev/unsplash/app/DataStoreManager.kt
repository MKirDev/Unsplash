package com.mkirdev.unsplash.app

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

class DataStoreManager(
    val onboardingDataStore: DataStore<Preferences>,
    val authDataStorage: DataStore<Preferences>
)