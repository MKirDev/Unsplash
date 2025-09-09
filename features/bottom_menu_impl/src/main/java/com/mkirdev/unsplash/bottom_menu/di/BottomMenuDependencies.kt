package com.mkirdev.unsplash.bottom_menu.di

import com.mkirdev.unsplash.collections.api.CollectionsFeatureApi
import com.mkirdev.unsplash.core.navigation.IconicTopDestinations
import com.mkirdev.unsplash.photo_explore.api.PhotoExploreFeatureApi
import com.mkirdev.unsplash.profile.api.ProfileFeatureApi

interface BottomMenuDependencies {
    val photoExploreFeatureApi: PhotoExploreFeatureApi
    val collectionsFeatureApi: CollectionsFeatureApi

    val profileFeatureApi: ProfileFeatureApi
    val iconicTopDestination: IconicTopDestinations
}