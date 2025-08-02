package com.mkirdev.unsplash.di

import android.content.Context
import com.mkirdev.unsplash.collections.api.navigation.CollectionsTopLevelDestination
import com.mkirdev.unsplash.core.navigation.TopDestinations
import com.mkirdev.unsplash.photo_feed.api.navigation.PhotoFeedTopLevelDestination
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
    fun provideTopDestinations(): TopDestinations {
        return TopDestinations(
            destinations = listOf(
                PhotoFeedTopLevelDestination,
                CollectionsTopLevelDestination
            )
        )
    }

    @Provides
    fun providerCacheScheduler(context: Context): CacheScheduler {
        return CacheScheduler(context)
    }

}