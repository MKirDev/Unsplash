package com.mkirdev.unsplash.app

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.work.WorkManager
import com.mkirdev.unsplash.auth.di.AuthDependenciesProvider
import com.mkirdev.unsplash.bottom_menu.di.BottomMenuDependenciesProvider
import com.mkirdev.unsplash.di.DaggerAppComponent
import com.mkirdev.unsplash.di.DaggerProvider
import com.mkirdev.unsplash.onboarding.di.OnboardingDependenciesProvider
import com.mkirdev.unsplash.photo_feed.di.PhotoFeedDependenciesProvider
import com.mkirdev.unsplash.workers.PhotoLikeWorker
import com.mkirdev.unsplash.workers.PhotoUnlikeWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val ONBOARDING_DATA_STORE = "onboarding_data_store"
private const val AUTH_DATA_STORE = "auth_data_store"
private const val PHOTOS_DATA_STORE = "photos_data_store"
class App : Application() {

    private val Context.onboardingDataStore: DataStore<Preferences> by preferencesDataStore(name = ONBOARDING_DATA_STORE)
    private val Context.authDataStore: DataStore<Preferences> by preferencesDataStore(name = AUTH_DATA_STORE)
    private val Context.photosDataStore: DataStore<Preferences> by preferencesDataStore(name = PHOTOS_DATA_STORE)

    override fun onCreate() {
        super.onCreate()
        initDagger()
        launchLikedPhotoCollector()
        launchUnlikedPhotoCollector()
    }

    private fun initDagger() {
        val dataStoreManager = DataStoreManager(
            onboardingDataStore = onboardingDataStore,
            authDataStore = authDataStore,
            photosDataStore = photosDataStore
        )
        val appComponent = DaggerAppComponent
            .builder()
            .context(this)
            .dataStoreManager(dataStoreManager)
            .build()
        DaggerProvider.appComponent = appComponent
        OnboardingDependenciesProvider.dependencies = appComponent
        AuthDependenciesProvider.dependencies = appComponent
        BottomMenuDependenciesProvider.dependencies = appComponent
        PhotoFeedDependenciesProvider.dependencies = appComponent
    }

    private fun launchLikedPhotoCollector() {

        val context = applicationContext

        val getLikedPhotoUseCase = DaggerProvider.appComponent.getLikedPhotoUseCase

        val dispatcher = Dispatchers.IO
        val coroutineScope = CoroutineScope(dispatcher)

        coroutineScope.launch {
            getLikedPhotoUseCase.execute().collect {
                if (it.isNotEmpty()) {
                    val workManager = WorkManager.getInstance(context)
                    PhotoLikeWorker.enqueueUniqueWork(workManager, it)
                }
            }
        }
    }

    private fun launchUnlikedPhotoCollector() {

        val context = applicationContext

        val getUnlikedPhotoUseCase = DaggerProvider.appComponent.getUnlikedPhotoUseCase

        val dispatcher = Dispatchers.IO
        val coroutineScope = CoroutineScope(dispatcher)

        coroutineScope.launch {
            getUnlikedPhotoUseCase.execute().collect {
                if (it.isNotEmpty()) {
                    val workManager = WorkManager.getInstance(context)
                    PhotoUnlikeWorker.enqueueUniqueWork(workManager, it)
                }
            }
        }
    }

}