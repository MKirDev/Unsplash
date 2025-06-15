package com.mkirdev.unsplash.onboarding.api

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.mkirdev.unsplash.core.contract.viewmodel.applyEffect
import com.mkirdev.unsplash.onboarding.di.DaggerOnboardingComponent
import com.mkirdev.unsplash.onboarding.di.OnboardingDependenciesProvider
import com.mkirdev.unsplash.onboarding.impl.OnboardingContract
import com.mkirdev.unsplash.onboarding.impl.OnboardingScreen
import com.mkirdev.unsplash.onboarding.impl.OnboardingViewModel
import com.mkirdev.unsplash.onboarding.navigation.OnboardingDestination
import javax.inject.Inject

class OnboardingFeatureApiImpl @Inject constructor(): OnboardingFeatureApi {
    override fun NavHostController.navigateToOnboarding() {
        navigate(route = OnboardingDestination.route)
    }

    override fun NavGraphBuilder.onboarding(onNavigateToAuth: () -> Unit) {
        composable(route = OnboardingDestination.route) {

            val onboardingComponent by remember {
                mutableStateOf(
                    DaggerOnboardingComponent.builder()
                        .addDependencies(OnboardingDependenciesProvider.dependencies)
                        .build()
                )
            }

            val viewModel: OnboardingViewModel = viewModel(
                factory = onboardingComponent.onboardingViewModelFactory
            )

            val contentCreationFeatureApi by remember {
                mutableStateOf(onboardingComponent.contentCreationFeatureApi)
            }

            val socialCollectionsFeatureApi by remember {
                mutableStateOf(onboardingComponent.socialCollectionsFeatureApi)
            }

            val uploadAndTrackFeatureApi by remember {
                mutableStateOf(onboardingComponent.uploadAndTrackFeatureApi)
            }

            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            viewModel.applyEffect(function = { effect ->
                when (effect) {
                    OnboardingContract.Effect.Auth -> onNavigateToAuth()
                    null -> Unit
                }
            })

            OnboardingScreen(
                uiState = uiState,
                contentCreationFeatureApi = contentCreationFeatureApi,
                socialCollectionsFeatureApi = socialCollectionsFeatureApi,
                uploadAndTrackFeatureApi = uploadAndTrackFeatureApi,
                onAuthClick = { viewModel.handleEvent(OnboardingContract.Event.AuthOpenedEvent) },
                onCloseFieldClick = { viewModel.handleEvent(OnboardingContract.Event.FieldClosedEvent) }
            )
        }
    }
}