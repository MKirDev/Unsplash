package com.mkirdev.unsplash.profile.api

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mkirdev.unsplash.core.contract.viewmodel.applyEffect
import com.mkirdev.unsplash.core.navigation.ProjectNavDestination
import com.mkirdev.unsplash.profile.impl.ProfileContract
import com.mkirdev.unsplash.profile.impl.ProfileScreen
import com.mkirdev.unsplash.profile.impl.ProfileViewModel

class ProfileFeatureApiImpl : ProfileFeatureApi {
    override fun NavGraphBuilder.profile(
        onLogout: () -> Unit,
        onNavigateToDetails: (String) -> Unit
    ) {
        composable(route = ProfileDestination.route) {
            val viewModel: ProfileViewModel = viewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            viewModel.applyEffect(function = { effect ->
                when (effect) {
                    ProfileContract.Effect.Exit -> onLogout()
                    is ProfileContract.Effect.PhotoDetails -> onNavigateToDetails(effect.photoId)
                    null -> Unit
                }
            })

            ProfileScreen(
                uiState = uiState,
                onPhotoItemClick = { viewModel.handleEvent(ProfileContract.Event.PhotoDetailsOpenedEvent(it)) },
                onLikeClick = { viewModel.handleEvent(ProfileContract.Event.PhotoLikedEvent(it)) },
                onRemoveLikeClick = { viewModel.handleEvent(ProfileContract.Event.PhotoUnlikedEvent(it)) },
                onDownloadClick = { viewModel.handleEvent(ProfileContract.Event.DownloadRequestedEvent(it)) },
                onLoadError = { viewModel.handleEvent(ProfileContract.Event.LoadingErrorEvent) },
                onCloseFieldClick = { viewModel.handleEvent(ProfileContract.Event.FieldClosedEvent) },
                onPagingCloseFieldClick = { viewModel.handleEvent(ProfileContract.Event.PagingFieldClosedEvent) },
                onExitIconClick = { viewModel.handleEvent(ProfileContract.Event.LogoutRequestedEvent) },
                onCanceledLogoutClick = { viewModel.handleEvent(ProfileContract.Event.LogoutCanceledEvent) },
                onConfirmedLogoutClick = { viewModel.handleEvent(ProfileContract.Event.LogoutConfirmedEvent) })
        }
    }

}

object ProfileDestination : ProjectNavDestination {
    override val route: String = "profile"
}