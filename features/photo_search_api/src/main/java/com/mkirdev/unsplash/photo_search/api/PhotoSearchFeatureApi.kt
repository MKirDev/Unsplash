package com.mkirdev.unsplash.photo_search.api

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

interface PhotoSearchFeatureApi {

    fun NavHostController.navigateToSearch()
    fun NavGraphBuilder.photoSearch(
        onNavigateToDetails: (String) -> Unit,
        onNavigateToFeed: () -> Unit
    )
}