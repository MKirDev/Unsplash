package com.mkirdev.unsplash.profile.api

import androidx.navigation.NavGraphBuilder

interface ProfileFeatureApi {
    fun NavGraphBuilder.profile(onNavigateToDetails: (String) -> Unit)

}