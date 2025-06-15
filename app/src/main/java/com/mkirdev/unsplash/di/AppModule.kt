package com.mkirdev.unsplash.di

import com.mkirdev.unsplash.core.navigation.TopDestinations
import com.mkirdev.unsplash.photo_feed.navigation.PhotoFeedTopLevelDestination
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
                PhotoFeedTopLevelDestination
            )
        )
    }

}