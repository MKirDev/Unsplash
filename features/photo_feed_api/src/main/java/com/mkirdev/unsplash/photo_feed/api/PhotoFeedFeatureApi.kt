package com.mkirdev.unsplash.photo_feed.api

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

interface PhotoFeedFeatureApi {

    fun NavHostController.navigateToFeed()
    fun NavGraphBuilder.photoFeed(
        onNavigateToDetails: (String) -> Unit,
        onNavigateToSearch: () -> Unit
    )
}