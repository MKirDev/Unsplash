package com.mkirdev.unsplash.photo_explore.api

import androidx.navigation.NavGraphBuilder

interface PhotoExploreFeatureApi {
    fun NavGraphBuilder.photoExplore(
        onNavigateToDetails: (String) -> Unit
    )
}