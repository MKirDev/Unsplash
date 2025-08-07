package com.mkirdev.unsplash.bottom_menu.di

import com.mkirdev.unsplash.collections.api.CollectionsFeatureApi
import com.mkirdev.unsplash.core.navigation.TopDestinations
import com.mkirdev.unsplash.photo_feed.api.PhotoFeedFeatureApi
import com.mkirdev.unsplash.profile.api.ProfileFeatureApi

interface BottomMenuDependencies {
    val photoFeedFeatureApi: PhotoFeedFeatureApi
    val collectionsFeatureApi: CollectionsFeatureApi

    val profileFeatureApi: ProfileFeatureApi
    val topLevelDestination: TopDestinations
}