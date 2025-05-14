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
import com.mkirdev.unsplash.details.impl.PhotoDetailsContract
import com.mkirdev.unsplash.details.impl.PhotoDetailsScreen
import com.mkirdev.unsplash.details.impl.PhotoDetailsViewModel

class DetailsFeatureApiImpl : DetailsFeatureApi {
    override fun NavHostController.navigateToDetails(photoId: String) {
        navigate("${DetailsDestination.route}/${photoId}")
    }

    override fun NavGraphBuilder.details(onNavigateUp: () -> Unit, onNavigateBack: () -> Unit) {
        composable(
            route = DetailsDestination.routeWithArgument,
            arguments = DetailsDestination.arguments
        ) {
            val viewModel: PhotoDetailsViewModel = viewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            viewModel.applyEffect(function = { effect ->
                when (effect) {
                    is PhotoDetailsContract.Effect.Location -> {

                    }
                    is PhotoDetailsContract.Effect.Share -> {

                    }
                    PhotoDetailsContract.Effect.UpPressed -> { onNavigateUp() }
                    PhotoDetailsContract.Effect.BackPressed -> { onNavigateBack() }
                    null -> Unit
                }
            })

            PhotoDetailsScreen(
                uiState = uiState,
                onShareClick = { viewModel.handleEvent(PhotoDetailsContract.Event.ShareEvent(it)) },
                onLikeClick = { viewModel.handleEvent(PhotoDetailsContract.Event.PhotoLikeEvent(it)) },
                onRemoveLikeClick = { viewModel.handleEvent(PhotoDetailsContract.Event.PhotoRemoveLikeEvent(it)) },
                onLocationClick = { viewModel.handleEvent(PhotoDetailsContract.Event.LocationEvent(it)) },
                onDownloadClick = { viewModel.handleEvent(PhotoDetailsContract.Event.DownloadEvent(it)) },
                onCloseFieldClick = { viewModel.handleEvent(PhotoDetailsContract.Event.FieldCloseEvent) },
                onNavigateUp = { viewModel.handleEvent(PhotoDetailsContract.Event.NavigateUpEvent) },
                onNavigateBack = { viewModel.handleEvent(PhotoDetailsContract.Event.NavigateBackEvent) }
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
