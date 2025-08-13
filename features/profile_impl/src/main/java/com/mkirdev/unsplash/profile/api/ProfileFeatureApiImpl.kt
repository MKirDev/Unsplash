package com.mkirdev.unsplash.profile.api

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mkirdev.unsplash.core.contract.viewmodel.applyEffect
import com.mkirdev.unsplash.profile.api.navigation.ProfileTopLevelDestination
import com.mkirdev.unsplash.profile.di.DaggerProfileComponent
import com.mkirdev.unsplash.profile.di.ProfileDependenciesProvider
import com.mkirdev.unsplash.profile.impl.ProfileContract
import com.mkirdev.unsplash.profile.impl.ProfileScreenWrapper
import com.mkirdev.unsplash.profile.impl.ProfileViewModel
import javax.inject.Inject

class ProfileFeatureApiImpl @Inject constructor() : ProfileFeatureApi {
    override fun NavGraphBuilder.profile(
        onLogout: () -> Unit,
        onNavigateToDetails: (String) -> Unit
    ) {
        composable(route = ProfileTopLevelDestination.route) {

            val profileComponent = remember {
                DaggerProfileComponent.builder()
                    .addDependencies(ProfileDependenciesProvider.dependencies)
                    .build()
            }

            val viewModel: ProfileViewModel = viewModel(
                factory = profileComponent.profileViewModelFactory
            )

            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            viewModel.applyEffect(function = { effect ->
                when (effect) {
                    ProfileContract.Effect.Exit -> onLogout()
                    is ProfileContract.Effect.PhotoDetails -> onNavigateToDetails(effect.photoId)
                    null -> Unit
                }
            })

            ProfileScreenWrapper(
                uiState = uiState,
                onPhotoItemClick = { viewModel.handleEvent(ProfileContract.Event.PhotoDetailsOpenedEvent(it)) },
                onLikeClick = { viewModel.handleEvent(ProfileContract.Event.PhotoLikedEvent(it)) },
                onRemoveLikeClick = { viewModel.handleEvent(ProfileContract.Event.PhotoUnlikedEvent(it)) },
                onDownloadClick = { viewModel.handleEvent(ProfileContract.Event.DownloadRequestedEvent(it)) },
                onLoadError = { viewModel.handleEvent(ProfileContract.Event.LoadingErrorEvent) },
                onCloseFieldClick = { viewModel.handleEvent(ProfileContract.Event.FieldClosedEvent) },
                onPagingCloseFieldClick = { viewModel.handleEvent(ProfileContract.Event.PagingFieldClosedEvent) },
                onPagingRetry = { viewModel.handleEvent(ProfileContract.Event.PagingRetryEvent(it)) },
                onPagingRefresh = { viewModel.handleEvent(ProfileContract.Event.PagingRefreshEvent(it)) },
                onExitIconClick = { viewModel.handleEvent(ProfileContract.Event.LogoutRequestedEvent) },
                onCanceledLogoutClick = { viewModel.handleEvent(ProfileContract.Event.LogoutCanceledEvent) },
                onConfirmedLogoutClick = { viewModel.handleEvent(ProfileContract.Event.LogoutConfirmedEvent) })
        }
    }

}