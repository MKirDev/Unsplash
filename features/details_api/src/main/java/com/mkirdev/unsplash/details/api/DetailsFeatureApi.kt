package com.mkirdev.unsplash.details.api

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

interface DetailsFeatureApi {
    fun NavHostController.navigateToDetails(photoId: String)

    fun NavGraphBuilder.details(onNavigateUp: () -> Unit, onNavigateBack: () -> Unit)
}