package com.mkirdev.unsplash.auth.api

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

interface AuthFeatureApi {
    fun NavHostController.navigateToAuth(code: String?)

    fun NavHostController.logout()

    fun NavGraphBuilder.auth(
        schema: String,
        host: String,
        path: String,
        onNavigateToBottomMenu: () -> Unit
    )
}