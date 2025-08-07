package com.mkirdev.unsplash.app

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.work.WorkManager
import com.mkirdev.unsplash.auth.di.AuthDependenciesProvider
import com.mkirdev.unsplash.bottom_menu.di.BottomMenuDependenciesProvider
import com.mkirdev.unsplash.collection_details.di.CollectionDetailsDependenciesProvider
import com.mkirdev.unsplash.collections.di.CollectionsDependenciesProvider
import com.mkirdev.unsplash.details.di.DetailsDependenciesProvider
import com.mkirdev.unsplash.di.DaggerAppComponent
import com.mkirdev.unsplash.di.DaggerProvider
import com.mkirdev.unsplash.onboarding.di.OnboardingDependenciesProvider
import com.mkirdev.unsplash.photo_feed.di.PhotoFeedDependenciesProvider
import com.mkirdev.unsplash.profile.di.ProfileDependenciesProvider
import com.mkirdev.unsplash.workers.PhotoDownloadWorker
import com.mkirdev.unsplash.workers.PhotoLikeWorker
import com.mkirdev.unsplash.workers.PhotoUnlikeWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

private const val ONBOARDING_DATA_STORE = "onboarding_data_store"
private const val AUTH_DATA_STORE = "auth_data_store"

private const val PREFERENCES_DATA_STORE = "preferences_data_store"

class App : Application() {

    private val Context.onboardingDataStore: DataStore<Preferences> by preferencesDataStore(name = ONBOARDING_DATA_STORE)
    private val Context.authDataStore: DataStore<Preferences> by preferencesDataStore(name = AUTH_DATA_STORE)

    private val Context.preferencesDataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_DATA_STORE)

    override fun onCreate() {
        super.onCreate()
        initDagger()
        launchCollectors()
        startAlarmManager()
    }

    private fun initDagger() {
        val dataStoreManager = DataStoreManager(
            onboardingDataStore = onboardingDataStore,
            authDataStore = authDataStore,
            preferencesDataStore = preferencesDataStore
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
        DetailsDependenciesProvider.dependencies = appComponent
        CollectionsDependenciesProvider.dependencies = appComponent
        CollectionDetailsDependenciesProvider.dependencies = appComponent
        ProfileDependenciesProvider.dependencies = appComponent
    }

    private fun launchCollectors() {
        val context = applicationContext
        val dispatcher = DaggerProvider.appComponent.coroutineDispatcher
        val coroutineScope = CoroutineScope(dispatcher)

        launchLikedPhotoCollector(context, coroutineScope)
        launchUnlikedPhotoCollector(context, coroutineScope)
        launchPhotoLinkCollector(context, coroutineScope)
    }

    private fun launchLikedPhotoCollector(context: Context, coroutineScope: CoroutineScope) {

        val getLikedPhotoUseCase = DaggerProvider.appComponent.getLikedPhotoUseCase

        coroutineScope.launch {
            getLikedPhotoUseCase.execute().collect {
                if (it.isNotEmpty()) {
                    val workManager = WorkManager.getInstance(context)
                    PhotoLikeWorker.enqueueUniqueWork(workManager, it)
                }
            }
        }
    }

    private fun launchUnlikedPhotoCollector(
        context: Context, coroutineScope: CoroutineScope
    ) {
        val getUnlikedPhotoUseCase = DaggerProvider.appComponent.getUnlikedPhotoUseCase

        coroutineScope.launch {
            getUnlikedPhotoUseCase.execute().collect {
                if (it.isNotEmpty()) {
                    val workManager = WorkManager.getInstance(context)
                    PhotoUnlikeWorker.enqueueUniqueWork(workManager, it)
                }
            }
        }
    }

    private fun launchPhotoLinkCollector(
        context: Context, coroutineScope: CoroutineScope
    ) {
        val getDownloadLinkUseCase = DaggerProvider.appComponent.getDownloadLinkUseCase

        coroutineScope.launch {
            getDownloadLinkUseCase.execute().collect {
                if (it.isNotEmpty()) {
                    val workManager = WorkManager.getInstance(context)
                    PhotoDownloadWorker.enqueueUniqueWork(workManager, it)
                }
            }
        }
    }

    private fun startAlarmManager() {
        val cacheScheduler = DaggerProvider.appComponent.cacheScheduler
        val getScheduleFlagUseCase = DaggerProvider.appComponent.getScheduleFlagUseCase
        val saveScheduleFlagUseCase = DaggerProvider.appComponent.saveScheduleFlagUseCase
        val deleteScheduleFlagUseCase = DaggerProvider.appComponent.deleteScheduleFlagUseCase
        val dispatcher = DaggerProvider.appComponent.coroutineDispatcher

        val coroutineScope = CoroutineScope(dispatcher)

        coroutineScope.launch {
            val flag = getScheduleFlagUseCase.execute()
            flag.collect {
//                if (it == null) {
//                    cacheScheduler.schedule()
//                    saveScheduleFlagUseCase.execute(true)
//                    Log.d("AAA","cacheScheduler $cacheScheduler started")
//                }
//                else if (!it) {
//                    cacheScheduler.cancel()
//                }
            }
        }
    }
}