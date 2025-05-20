package com.mkirdev.unsplash.onboarding.api

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

interface OnboardingFeatureApi {
    fun NavHostController.navigateToOnboarding()

    fun NavGraphBuilder.onboarding(onNavigateToAuth: () -> Unit)
}