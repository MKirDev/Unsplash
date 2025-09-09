package com.mkirdev.unsplash.di

import android.content.Context
import com.mkirdev.unsplash.collections.api.navigation.CollectionsTopLevelDestination
import com.mkirdev.unsplash.core.navigation.IconicTopDestinations
import com.mkirdev.unsplash.domain.usecases.auth.GetLogoutEventUseCase
import com.mkirdev.unsplash.domain.usecases.auth.GetSavedTokenUseCase
import com.mkirdev.unsplash.domain.usecases.onboarding.GetOnboardingFlagUseCase
import com.mkirdev.unsplash.navigation.MainViewModelFactory
import com.mkirdev.unsplash.photo_explore.api.navigation.PhotoExploreTopLevelDestination
import com.mkirdev.unsplash.profile.api.navigation.ProfileTopLevelDestination
import com.mkirdev.unsplash.schedulers.CacheScheduler
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
class AppModule {

    @Provides
    fun provideCoroutineDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    @Provides
    fun provideIconicTopDestinations(): IconicTopDestinations {
        return IconicTopDestinations(
            destinations = listOf(
                PhotoExploreTopLevelDestination,
                CollectionsTopLevelDestination,
                ProfileTopLevelDestination
            )
        )
    }

    @Provides
    fun provideCacheScheduler(context: Context): CacheScheduler {
        return CacheScheduler(context)
    }

    @Provides
    fun provideMainViewModelFactory(
        getLogoutEventUseCase: GetLogoutEventUseCase,
        getSavedTokenUseCase: GetSavedTokenUseCase,
        getOnboardingFlagUseCase: GetOnboardingFlagUseCase
    ): MainViewModelFactory {
        return MainViewModelFactory(
            getLogoutEventUseCase = getLogoutEventUseCase,
            getSavedTokenUseCase = getSavedTokenUseCase,
            getOnboardingFlagUseCase = getOnboardingFlagUseCase
        )
    }

}