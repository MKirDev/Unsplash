package com.mkirdev.unsplash.app

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

class DataStoreManager(
    val onboardingDataStore: DataStore<Preferences>,
    val authDataStore: DataStore<Preferences>,
    val photosDataStore: DataStore<Preferences>
)