package com.mkirdev.unsplash.photo_search.api

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.mkirdev.unsplash.core.contract.viewmodel.applyEffect
import com.mkirdev.unsplash.photo_search.api.navigation.PhotoSearchDestination
import com.mkirdev.unsplash.photo_search.di.DaggerPhotoSearchComponent
import com.mkirdev.unsplash.photo_search.di.PhotoSearchDependenciesProvider
import com.mkirdev.unsplash.photo_search.impl.PhotoSearchContract
import com.mkirdev.unsplash.photo_search.impl.PhotoSearchScreenWrapper
import com.mkirdev.unsplash.photo_search.impl.PhotoSearchViewModel
import javax.inject.Inject

class PhotoSearchFeatureApiImpl @Inject constructor(): PhotoSearchFeatureApi {

    override fun NavHostController.navigateToSearch() {
        navigate(PhotoSearchDestination.route) {
            popUpTo(graph.startDestinationId) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    override fun NavGraphBuilder.photoSearch(
        onNavigateToDetails: (String) -> Unit,
        onNavigateToFeed: () -> Unit,
    ) {
        composable(
            route = PhotoSearchDestination.route,
            enterTransition = {
                fadeIn(animationSpec = tween(300))
            },
            exitTransition = {
                fadeOut(animationSpec = tween(300))
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(300))
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(300))
            }
        ) {

            val photoSearchComponent = remember {
                DaggerPhotoSearchComponent.builder()
                    .addDependencies(PhotoSearchDependenciesProvider.dependencies)
                    .build()
            }

            val viewModel: PhotoSearchViewModel = viewModel(
                factory = photoSearchComponent.photoSearchViewModelFactory
            )

            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            viewModel.applyEffect(
                function = { effect ->
                    when (effect) {
                        is PhotoSearchContract.Effect.Details -> {
                            onNavigateToDetails(effect.photoId)
                        }

                        PhotoSearchContract.Effect.Feed -> {
                            onNavigateToFeed()
                        }
                        null -> Unit
                    }
                }
            )

            PhotoSearchScreenWrapper(
                uiState = uiState,
                onSearch = { viewModel.handleEvent(PhotoSearchContract.Event.SearchEvent(it)) },
                onFeedClick = { viewModel.handleEvent(PhotoSearchContract.Event.PhotoFeedClickedEvent) },
                onPhotoClick = { viewModel.handleEvent(PhotoSearchContract.Event.PhotoDetailsOpenedEvent(it)) },
                onLikeClick = { viewModel.handleEvent(PhotoSearchContract.Event.PhotoLikedEvent(it)) },
                onRemoveLikeClick = { viewModel.handleEvent(PhotoSearchContract.Event.PhotoUnlikedEvent(it)) },
                onLoadError = { viewModel.handleEvent(PhotoSearchContract.Event.LoadingErrorEvent) },
                onSaveScrollState = { viewModel.handleEvent(PhotoSearchContract.Event.ScrollStateSavedEvent(it)) },
                onCloseFieldClick = { viewModel.handleEvent(PhotoSearchContract.Event.FieldClosedEvent) },
                onPagingCloseField = { viewModel.handleEvent(PhotoSearchContract.Event.PagingFieldClosedEvent) },
                onPagingRetry = { viewModel.handleEvent(PhotoSearchContract.Event.PagingRetryEvent(it)) }
            )
        }
    }
}