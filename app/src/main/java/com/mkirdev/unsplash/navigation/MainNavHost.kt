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
import com.mkirdev.unsplash.onboarding.navigation.OnboardingDestination
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

private const val EMPTY_CODE = ""
@Composable
fun MainNavHost(
    startedFromDeepLink: DeepLink = rememberStartedFromDeeplink(),
    navController: NavHostController = rememberNavController(),
    viewModel: MainViewModel = viewModel()
) {

    val context = LocalContext.current

    LaunchedEffect(startedFromDeepLink) {
        if (startedFromDeepLink.hasDeepLink()) {
            val externalScheme = context.getString(R.string.app_external_scheme)
            val internalScheme = context.getString(R.string.app_internal_scheme)
            val intent = startedFromDeepLink.intent
            intent.data = intent.data.toString().replace(externalScheme, internalScheme).toUri()
            navController.handleDeepLink(intent)
        }
    }

    val onboardingFeatureApi by remember {
        mutableStateOf(DaggerProvider.appComponent.onboardingFeatureApi)
    }

    val authFeatureApi by remember {
        mutableStateOf(DaggerProvider.appComponent.authFeatureApi)
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
                }
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

@Composable
private fun rememberStartedFromDeeplink(): DeepLink {
    val context = LocalContext.current
    return remember { isStartedFromDeepLink(context) }
}

private fun isStartedFromDeepLink(context: Context): DeepLink {
    val intent = (context as Activity).intent
    return if (intent.action == Intent.ACTION_VIEW && intent.data != null) {
        DeepLink.Link(intent = intent)
    } else {
        DeepLink.None
    }
}