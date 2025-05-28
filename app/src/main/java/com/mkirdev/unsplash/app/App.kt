package com.mkirdev.unsplash.app

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.mkirdev.unsplash.auth.di.AuthDependenciesProvider
import com.mkirdev.unsplash.di.DaggerAppComponent
import com.mkirdev.unsplash.di.DaggerProvider
import com.mkirdev.unsplash.onboarding.di.OnboardingDependenciesProvider

private const val ONBOARDING_DATA_STORE = "onboarding_data_store"
private const val AUTH_DATA_STORE = "auth_data_store"
class App : Application() {

    private val Context.onboardingDataStore: DataStore<Preferences> by preferencesDataStore(name = ONBOARDING_DATA_STORE)
    private val Context.authDataStore: DataStore<Preferences> by preferencesDataStore(name = AUTH_DATA_STORE)

    override fun onCreate() {
        super.onCreate()
        initDagger()
    }

    private fun initDagger() {
        val dataStoreManager = DataStoreManager(
            onboardingDataStore = onboardingDataStore,
            authDataStorage = authDataStore
        )
        val appComponent = DaggerAppComponent
            .builder()
            .context(this)
            .dataStoreManager(dataStoreManager)
            .build()
        DaggerProvider.appComponent = appComponent
        OnboardingDependenciesProvider.dependencies = appComponent
        AuthDependenciesProvider.dependencies = appComponent
    }

}