package com.mkirdev.unsplash.navigation

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.collection.isNotEmpty
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.mkirdev.unsplash.core.navigation.R
import com.mkirdev.unsplash.di.DaggerProvider
import com.mkirdev.unsplash.onboarding.api.navigation.OnboardingDestination
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.snapshotFlow
import com.mkirdev.unsplash.auth.api.navigation.AuthDestination
import com.mkirdev.unsplash.bottom_menu.api.navigation.BottomMenuDestination
import kotlinx.coroutines.flow.first

@Composable
fun MainNavHost(
    deepLinkIntent: State<Intent?>,
    viewModel: MainViewModel,
    navController: NavHostController = rememberNavController()
) {

    val context = LocalContext.current

    val onboardingCompleted by viewModel.onboardingState.collectAsState()
    val authCompleted by viewModel.authState.collectAsState()

    val isReady by remember {
        derivedStateOf {
            onboardingCompleted != null && authCompleted != null
        }
    }

    when (isReady) {
        true -> {

            val startDestination by remember {
                derivedStateOf {
                    if (onboardingCompleted == true && authCompleted == true) BottomMenuDestination.graph
                    else if (authCompleted == false && onboardingCompleted == true) AuthDestination.routeWithArgs
                    else OnboardingDestination.route
                }
            }

            val onboardingFeatureApi = remember {
                DaggerProvider.appComponent.onboardingFeatureApi
            }

            val authFeatureApi = remember {
                DaggerProvider.appComponent.authFeatureApi
            }

            val bottomMenuFeatureApi = remember {
                DaggerProvider.appComponent.bottomMenuFeatureApi
            }

            val detailsFeatureApi = remember {
                DaggerProvider.appComponent.detailsFeatureApi
            }

            val collectionDetailsFeatureApi = remember {
                DaggerProvider.appComponent.collectionDetailsFeatureApi
            }

            LaunchedEffect(deepLinkIntent.value) {
                val deepLink = deepLinkIntent.value.toDeepLink()
                if (deepLink.hasDeepLink()) {
                    val intent = deepLink.intent
                    val externalScheme = context.getString(R.string.app_external_scheme)
                    val internalScheme = context.getString(R.string.app_internal_scheme)
                    intent.data = intent.data.toString().replace(externalScheme, internalScheme).toUri()

                    snapshotFlow { navController.graph.nodes.isNotEmpty() }
                        .first { it }

                    navController.handleDeepLink(intent)

                    (context as Activity).intent.data = null
                }
            }

            LaunchedEffect(Unit) {
                viewModel.logoutState.collect {
                    with(authFeatureApi) {
                        navController.logoutWithError()
                    }
                }
            }


            NavHost(
                navController = navController,
                startDestination = startDestination
            ) {
                with(onboardingFeatureApi) {
                    onboarding(
                        onNavigateToAuth = {
                            with(authFeatureApi) {
                                navController.navigateToAuth()
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
                        onLogout = {
                            with(authFeatureApi) {
                                navController.logout()
                            }
                        }
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

        false -> {}
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