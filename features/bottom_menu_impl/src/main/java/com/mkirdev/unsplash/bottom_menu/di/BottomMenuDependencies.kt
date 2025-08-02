package com.mkirdev.unsplash.bottom_menu.di

import com.mkirdev.unsplash.collections.api.CollectionsFeatureApi
import com.mkirdev.unsplash.core.navigation.TopDestinations
import com.mkirdev.unsplash.photo_feed.api.PhotoFeedFeatureApi

interface BottomMenuDependencies {
    val photoFeedFeatureApi: PhotoFeedFeatureApi

    val collectionsFeatureApi: CollectionsFeatureApi
    val topLevelDestination: TopDestinations
}