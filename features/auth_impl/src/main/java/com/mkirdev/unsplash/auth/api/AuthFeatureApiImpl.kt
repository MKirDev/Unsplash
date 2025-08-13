package com.mkirdev.unsplash.auth.api

import android.content.Context
import android.util.Log
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.mkirdev.unsplash.auth.di.AuthDependenciesProvider
import com.mkirdev.unsplash.auth.di.DaggerAuthComponent
import com.mkirdev.unsplash.auth.impl.AuthContract
import com.mkirdev.unsplash.auth.impl.AuthScreen
import com.mkirdev.unsplash.auth.impl.AuthViewModel
import com.mkirdev.unsplash.auth.api.navigation.AuthDestination
import com.mkirdev.unsplash.core.contract.viewmodel.applyEffect
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationService
import javax.inject.Inject

class AuthFeatureApiImpl @Inject constructor(): AuthFeatureApi {
    override fun NavHostController.navigateToAuth() {
        popBackStack()
        navigate(AuthDestination.routeWithArgs)
        this.context.getString(com.mkirdev.unsplash.core.navigation.R.string.app_internal_scheme)
    }

    override fun NavHostController.logout() {
        popBackStack()
        navigate(AuthDestination.routeWithArgs)
    }

    override fun NavHostController.logoutWithError() {
        popBackStack()
        navigate("${AuthDestination.route}?code=&error=${true}")
    }

    override fun NavGraphBuilder.auth(
        schema: String,
        host: String,
        path: String,
        onNavigateToBottomMenu: () -> Unit
    ) {
        composable(
            route = AuthDestination.routeWithArgs,
            arguments = AuthDestination.arguments,
            deepLinks = AuthDestination.deeplink(schema = schema, host =  host, path = path)
        ) {
            val argString = it.arguments?.getString(AuthDestination.codeArg)
            val argBoolean = it.arguments?.getBoolean(AuthDestination.errorArg)

            val authComponent = remember {
                DaggerAuthComponent.builder()
                    .addDependencies(AuthDependenciesProvider.dependencies)
                    .build()
            }

            val authService = remember {
                authComponent.authService
            }

            val viewModel: AuthViewModel = viewModel(
                factory = authComponent.authViewModelFactory
            )
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            val context = LocalContext.current

            LaunchedEffect(Unit) {
                Log.d("AAA","argS - $argString")
                Log.d("AAA","argB - $argBoolean")
                if (argBoolean == true) {
                    viewModel.handleEvent(AuthContract.Event.CodeReceivedFailureEvent(
                        context.getString(com.mkirdev.unsplash.core.ui.R.string.auth_network_error)
                    ))
                } else {
                    if (!argString.isNullOrEmpty()) {
                        argString.let {
                            viewModel.handleEvent(AuthContract.Event.CodeReceivedSuccessEvent(it))
                        }
                    }
                }
            }

            DisposableEffect(Unit) {
                onDispose {
                    authService.dispose()
                }
            }

            viewModel.applyEffect(
                function = { effect ->
                    when (effect) {
                        is AuthContract.Effect.Auth -> {
                            onAuthEffect(
                                context,
                                authService = authService,
                                authRequest = effect.authRequest
                            )
                        }

                        AuthContract.Effect.PostAuth -> {
                            // preferencesStore
                            onNavigateToBottomMenu()
                        }
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

    private fun onAuthEffect(
        context: Context,
        authService: AuthorizationService,
        authRequest: String
    ) {
        val customTabsIntent = CustomTabsIntent.Builder().build()
        val authorizationRequest = AuthorizationRequest.jsonDeserialize(authRequest)

        val openAuthPageIntent = authService.getAuthorizationRequestIntent(authorizationRequest, customTabsIntent)

        context.startActivity(openAuthPageIntent)
    }
}