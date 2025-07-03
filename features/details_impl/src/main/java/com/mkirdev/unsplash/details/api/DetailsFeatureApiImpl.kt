package com.mkirdev.unsplash.details.api

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mkirdev.unsplash.core.contract.viewmodel.applyEffect
import com.mkirdev.unsplash.core.navigation.ProjectNavDestination
import com.mkirdev.unsplash.details.impl.DetailsContract
import com.mkirdev.unsplash.details.impl.DetailsViewModel
import com.mkirdev.unsplash.details.impl.PhotoDetailsScreenWrapper

class DetailsFeatureApiImpl : DetailsFeatureApi {
    override fun NavHostController.navigateToDetails(photoId: String) {
        navigate("${DetailsDestination.route}/${photoId}")
    }

    override fun NavGraphBuilder.details(onNavigateUp: () -> Unit, onNavigateBack: () -> Unit) {
        composable(
            route = DetailsDestination.routeWithArgument,
            arguments = DetailsDestination.arguments
        ) {
            val viewModel: DetailsViewModel = viewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            viewModel.applyEffect(function = { effect ->
                when (effect) {
                    is DetailsContract.Effect.Location -> {

                    }
                    is DetailsContract.Effect.Share -> {

                    }
                    DetailsContract.Effect.UpPressed -> { onNavigateUp() }
                    DetailsContract.Effect.BackPressed -> { onNavigateBack() }
                    null -> Unit
                }
            })

            PhotoDetailsScreenWrapper(
                uiState = uiState,
                onShareClick = { viewModel.handleEvent(DetailsContract.Event.ShareRequestedEvent(it)) },
                onLikeClick = { viewModel.handleEvent(DetailsContract.Event.PhotoLikedEvent(it)) },
                onRemoveLikeClick = { viewModel.handleEvent(DetailsContract.Event.PhotoUnlikedEvent(it)) },
                onLocationClick = { viewModel.handleEvent(DetailsContract.Event.LocationOpenedEvent(it)) },
                onDownloadClick = { viewModel.handleEvent(DetailsContract.Event.DownloadRequestedEvent(it)) },
                onCloseFieldClick = { viewModel.handleEvent(DetailsContract.Event.FieldClosedEvent) },
                onNavigateUp = { viewModel.handleEvent(DetailsContract.Event.NavigateUpEvent) },
                onNavigateBack = { viewModel.handleEvent(DetailsContract.Event.NavigateBackEvent) }
                )
        }
    }
}

object DetailsDestination : ProjectNavDestination {
    private const val argumentName = "photoId"
    override val route: String = "details"
    val routeWithArgument = "${route}/{${argumentName}}"
    val arguments = listOf(navArgument(argumentName) { type = NavType.StringType })
}
