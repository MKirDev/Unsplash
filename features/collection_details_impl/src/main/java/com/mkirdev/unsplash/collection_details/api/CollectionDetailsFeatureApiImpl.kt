package com.mkirdev.unsplash.collection_details.api

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mkirdev.unsplash.collection_details.impl.CollectionDetailsContract
import com.mkirdev.unsplash.collection_details.impl.CollectionDetailsScreen
import com.mkirdev.unsplash.collection_details.impl.CollectionDetailsViewModel
import com.mkirdev.unsplash.core.contract.viewmodel.applyEffect
import com.mkirdev.unsplash.core.navigation.ProjectNavDestination

class CollectionDetailsFeatureApiImpl : CollectionDetailsFeatureApi {
    override fun NavController.navigateToCollectionDetails(collectionId: String) {
        navigate("${CollectionDetailsDestination.route}/${collectionId}")
    }

    override fun NavGraphBuilder.collectionDetails(
        onNavigateToDetails: (String) -> Unit,
        onNavigateUp: () -> Unit,
        onNavigateBack: () -> Unit
    ) {
        composable(
            route = CollectionDetailsDestination.routeWithArgument,
            arguments = CollectionDetailsDestination.arguments
        ) {
            val viewModel: CollectionDetailsViewModel = viewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            viewModel.applyEffect(function = { effect ->
                when (effect) {
                    CollectionDetailsContract.Effect.BackPressed -> onNavigateBack()
                    CollectionDetailsContract.Effect.UpPressed -> onNavigateUp()
                    is CollectionDetailsContract.Effect.PhotoDetails -> onNavigateToDetails(effect.photoId)
                    null -> Unit
                }
            })

            CollectionDetailsScreen(
                uiState = uiState,
                onPhotoItemClick = { viewModel.handleEvent(CollectionDetailsContract.Event.PhotoDetailsEvent(it)) },
                onLikeClick = { viewModel.handleEvent(CollectionDetailsContract.Event.PhotoLikeEvent(it)) },
                onRemoveLikeClick = { viewModel.handleEvent(CollectionDetailsContract.Event.PhotoRemoveLikeEvent(it)) },
                onDownloadClick = { viewModel.handleEvent(CollectionDetailsContract.Event.DownloadEvent(it)) },
                onLoadError = { viewModel.handleEvent(CollectionDetailsContract.Event.ErrorLoadEvent)},
                onCloseFieldClick = { viewModel.handleEvent(CollectionDetailsContract.Event.FieldCloseEvent) },
                onPagingCloseFieldClick = { viewModel.handleEvent(CollectionDetailsContract.Event.PagingFieldCloseEvent) },
                onNavigateUp = { viewModel.handleEvent(CollectionDetailsContract.Event.NavigateUpEvent) },
                onNavigateBack = { viewModel.handleEvent(CollectionDetailsContract.Event.NavigateBackEvent) }
            )
        }
    }
}

object CollectionDetailsDestination : ProjectNavDestination {
    private const val argumentName = "collectionId"
    override val route: String = "collection_details"
    val routeWithArgument = "${route}/{${argumentName}}"
    val arguments = listOf(navArgument(argumentName) { type = NavType.StringType })
}


