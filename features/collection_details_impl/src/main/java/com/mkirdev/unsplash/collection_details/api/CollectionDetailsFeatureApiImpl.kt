package com.mkirdev.unsplash.collection_details.api

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mkirdev.unsplash.collection_details.api.navigation.CollectionDetailsDestination
import com.mkirdev.unsplash.collection_details.di.CollectionDetailsDependenciesProvider
import com.mkirdev.unsplash.collection_details.di.DaggerCollectionDetailsComponent
import com.mkirdev.unsplash.collection_details.impl.CollectionDetailsContract
import com.mkirdev.unsplash.collection_details.impl.CollectionDetailsScreenWrapper
import com.mkirdev.unsplash.collection_details.impl.CollectionDetailsViewModel
import com.mkirdev.unsplash.core.contract.viewmodel.applyEffect
import javax.inject.Inject

class CollectionDetailsFeatureApiImpl @Inject constructor() : CollectionDetailsFeatureApi {
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
        ) { navStackEntry ->

            val collectionDetailsComponent = remember {
                DaggerCollectionDetailsComponent.builder()
                    .addDependencies(CollectionDetailsDependenciesProvider.dependencies)
                    .build()
            }

            val collectionId = navStackEntry.arguments?.getString(CollectionDetailsDestination.argumentName)
                ?: throw IllegalArgumentException("Id collection is null")

            val viewModel: CollectionDetailsViewModel = viewModel(
                factory = collectionDetailsComponent.factoryAssisted.create(collectionId)
            )

            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            viewModel.applyEffect(function = { effect ->
                when (effect) {
                    CollectionDetailsContract.Effect.BackPressed -> onNavigateBack()
                    CollectionDetailsContract.Effect.UpPressed -> onNavigateUp()
                    is CollectionDetailsContract.Effect.PhotoDetails -> onNavigateToDetails(effect.photoId)
                    null -> Unit
                }
            })

            CollectionDetailsScreenWrapper(
                uiState = uiState,
                onPhotoItemClick = {
                    viewModel.handleEvent(
                        CollectionDetailsContract.Event.PhotoDetailsOpenedEvent(
                            it
                        )
                    )
                },
                onLikeClick = {
                    viewModel.handleEvent(
                        CollectionDetailsContract.Event.PhotoLikedEvent(
                            it
                        )
                    )
                },
                onRemoveLikeClick = {
                    viewModel.handleEvent(
                        CollectionDetailsContract.Event.PhotoUnlikedEvent(
                            it
                        )
                    )
                },
                onDownloadClick = {
                    viewModel.handleEvent(
                        CollectionDetailsContract.Event.DownloadRequestedEvent(
                            it
                        )
                    )
                },
                onLoadError = { viewModel.handleEvent(CollectionDetailsContract.Event.LoadingErrorEvent) },
                onCloseFieldClick = { viewModel.handleEvent(CollectionDetailsContract.Event.FieldClosedEvent) },
                onPagingCloseFieldClick = { viewModel.handleEvent(CollectionDetailsContract.Event.PagingFieldClosedEvent) },
                onPagingRetry = {
                    viewModel.handleEvent(
                        CollectionDetailsContract.Event.PagingRetryEvent(
                            it
                        )
                    )
                },
                onNavigateUp = { viewModel.handleEvent(CollectionDetailsContract.Event.NavigateUpEvent) },
                onNavigateBack = { viewModel.handleEvent(CollectionDetailsContract.Event.NavigateBackEvent) }
            )
        }
    }
}

