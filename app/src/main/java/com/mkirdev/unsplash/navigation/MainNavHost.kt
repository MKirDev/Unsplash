package com.mkirdev.unsplash.navigation

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.mkirdev.unsplash.core.navigation.R
import com.mkirdev.unsplash.di.DaggerProvider
import com.mkirdev.unsplash.onboarding.api.navigation.OnboardingDestination
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract
import androidx.compose.runtime.State

private const val EMPTY_CODE = ""
@Composable
fun MainNavHost(
    deepLinkIntent: State<Intent?>,
    navController: NavHostController = rememberNavController(),
    viewModel: MainViewModel = viewModel()
) {

    val context = LocalContext.current

    LaunchedEffect(deepLinkIntent.value) {
        val deepLink = deepLinkIntent.value.toDeepLink()
        if (deepLink.hasDeepLink()) {
            val intent = deepLink.intent
            val externalScheme = context.getString(R.string.app_external_scheme)
            val internalScheme = context.getString(R.string.app_internal_scheme)
            intent.data = intent.data.toString().replace(externalScheme, internalScheme).toUri()
            navController.handleDeepLink(intent)

            (context as Activity).intent.data = null
        }
    }

    val onboardingFeatureApi by remember {
        mutableStateOf(DaggerProvider.appComponent.onboardingFeatureApi)
    }

    val authFeatureApi by remember {
        mutableStateOf(DaggerProvider.appComponent.authFeatureApi)
    }

    val bottomMenuFeatureApi by remember {
        mutableStateOf(DaggerProvider.appComponent.bottomMenuFeatureApi)
    }

    val detailsFeatureApi by remember {
        mutableStateOf(DaggerProvider.appComponent.detailsFeatureApi)
    }

    val collectionDetailsFeatureApi by remember {
        mutableStateOf(DaggerProvider.appComponent.collectionDetailsFeatureApi)
    }


    NavHost(
        navController = navController,
        startDestination = OnboardingDestination.route
    ) {
        with(onboardingFeatureApi) {
            onboarding(
                onNavigateToAuth = {
                    with(authFeatureApi) {
                        navController.navigateToAuth(EMPTY_CODE)
                    }
                }
            )
        }

        with(authFeatureApi) {
            auth(
                schema = context.getString(R.string.app_internal_scheme),
                host = context.getString(R.string.host),
                path = context.getString(R.string.path),
                onNavigateToBottomMenu = {
                    with(bottomMenuFeatureApi) {
                        navController.navigateToBottomMenu()
                    }
                }
            )
        }

        with(bottomMenuFeatureApi) {
            bottomMenu(
                onNavigateToPhotoDetails = {
                    with(detailsFeatureApi) {
                        navController.navigateToDetails(it)
                    }
                },
                onNavigateToCollectionDetails = {
                    with(collectionDetailsFeatureApi) {
                        navController.navigateToCollectionDetails(it)
                    }
                },
                onLogout = {}
            )
        }

        with(detailsFeatureApi) {
            details(
                onNavigateUp = { navController.popBackStack() },
                onNavigateBack = { navController.popBackStack() }
            )
        }

        with(collectionDetailsFeatureApi) {
            collectionDetails(
                onNavigateToDetails = {
                    with(detailsFeatureApi) {
                        navController.navigateToDetails(it)
                    }
                },
                onNavigateUp = { navController.popBackStack() },
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}

sealed class DeepLink {
    @OptIn(ExperimentalContracts::class)
    fun hasDeepLink(): Boolean {
        contract {
            returns(true) implies (this@DeepLink is Link)
            returns(false) implies (this@DeepLink is None)
        }
        return this is Link
    }

    data object None : DeepLink()
    data class Link(val intent: Intent) : DeepLink()
}

private fun Intent?.toDeepLink(): DeepLink {
    return if (this != null && action == Intent.ACTION_VIEW && data != null) {
        DeepLink.Link(this)
    } else {
        DeepLink.None
    }
}