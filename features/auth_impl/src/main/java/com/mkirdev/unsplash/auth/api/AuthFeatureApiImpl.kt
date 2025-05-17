package com.mkirdev.unsplash.auth.api

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.mkirdev.unsplash.auth.impl.AuthContract
import com.mkirdev.unsplash.auth.impl.AuthScreen
import com.mkirdev.unsplash.auth.impl.AuthViewModel
import com.mkirdev.unsplash.core.contract.viewmodel.applyEffect
import com.mkirdev.unsplash.core.navigation.ProjectNavDestination

class AuthFeatureApiImpl : AuthFeatureApi {
    override fun NavHostController.navigateToAuth() {
        navigate(AuthDestination.route)
    }

    override fun NavGraphBuilder.auth(onNavigateToBottomMenu: () -> Unit) {
        composable(route = AuthDestination.route) {
            val viewModel: AuthViewModel = viewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            viewModel.applyEffect(
                function = { effect ->
                    when (effect) {
                        AuthContract.Effect.Auth -> {}
                        AuthContract.Effect.PostAuth -> onNavigateToBottomMenu()
                        AuthContract.Effect.BackPressed -> Unit
                        null -> Unit
                    }
                }
            )
            AuthScreen(
                uiState = uiState,
                onAuthClick = { viewModel.handleEvent(AuthContract.Event.AuthRequestedEvent) },
                onNotificationClick = { viewModel.handleEvent(AuthContract.Event.NotificationReceivedEvent) }
            )
        }
    }
}

object AuthDestination : ProjectNavDestination {
    override val route: String = "start"
}