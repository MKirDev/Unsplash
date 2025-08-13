package com.mkirdev.unsplash.auth.api

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

interface AuthFeatureApi {
    fun NavHostController.navigateToAuth()

    fun NavHostController.logout()

    fun NavHostController.logoutWithError()

    fun NavGraphBuilder.auth(
        schema: String,
        host: String,
        path: String,
        onNavigateToBottomMenu: () -> Unit
    )
}