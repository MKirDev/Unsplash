package com.mkirdev.unsplash.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.mkirdev.unsplash.di.DaggerProvider
import com.mkirdev.unsplash.onboarding.navigation.OnboardingDestination

@Composable
fun MainNavHost(
    navController: NavHostController = rememberNavController(),
    viewModel: MainViewModel = viewModel()
) {

    val onboardingFeatureApi by remember {
        mutableStateOf(DaggerProvider.appComponent.onboardingFeatureApi)
    }


    NavHost(
        navController = navController,
        startDestination = OnboardingDestination.route
    ) {
        with(onboardingFeatureApi) {
            onboarding(
                onNavigateToAuth = {
                }
            )
        }
    }
}