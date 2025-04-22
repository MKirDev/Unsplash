package com.mkirdev.unsplash.auth.api

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

interface AuthFeatureApi {
    fun NavHostController.navigateToAuth()

    fun NavGraphBuilder.auth(onNavigateToBottomMenu: () -> Unit)
}