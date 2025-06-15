package com.mkirdev.unsplash.bottom_menu.api

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

interface BottomMenuFeatureApi {
    fun NavHostController.navigateToBottomMenu()

    fun NavGraphBuilder.bottomMenu(
        onNavigateToPhotoDetails: (String) -> Unit,
        onNavigateToCollectionDetails: (String) -> Unit,
        onNavigateUp: () -> Unit,
        onNavigateBack: () -> Unit,
        onLogout: () -> Unit,
    )
}