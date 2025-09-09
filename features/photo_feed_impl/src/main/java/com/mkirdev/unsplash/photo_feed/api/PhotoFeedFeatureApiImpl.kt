package com.mkirdev.unsplash.photo_feed.api

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
import com.mkirdev.unsplash.photo_feed.di.DaggerPhotoFeedComponent
import com.mkirdev.unsplash.photo_feed.di.PhotoFeedDependenciesProvider
import com.mkirdev.unsplash.photo_feed.impl.PhotoFeedContract
import com.mkirdev.unsplash.photo_feed.impl.PhotoFeedScreenWrapper
import com.mkirdev.unsplash.photo_feed.impl.PhotoFeedViewModel
import com.mkirdev.unsplash.photo_feed.api.navigation.PhotoFeedDestination
import javax.inject.Inject

class PhotoFeedFeatureApiImpl @Inject constructor() : PhotoFeedFeatureApi {

    override fun NavHostController.navigateToFeed() {
        navigate(PhotoFeedDestination.route) {
            popUpTo(graph.startDestinationId) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    override fun NavGraphBuilder.photoFeed(
        onNavigateToDetails: (String) -> Unit,
        onNavigateToSearch: () -> Unit
    ) {
        composable(
            route = PhotoFeedDestination.route,
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

            val photoFeedComponent = remember {
                DaggerPhotoFeedComponent.builder()
                    .addDependencies(PhotoFeedDependenciesProvider.dependencies)
                    .build()
            }

            val viewModel: PhotoFeedViewModel = viewModel(
                factory = photoFeedComponent.photoFeedViewModelFactory
            )
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            viewModel.applyEffect(
                function = { effect ->
                    when (effect) {
                        is PhotoFeedContract.Effect.Details -> {
                            onNavigateToDetails(effect.photoId)
                        }

                        PhotoFeedContract.Effect.Search -> {
                            onNavigateToSearch()
                        }

                        null -> Unit
                    }
                })

            PhotoFeedScreenWrapper(
                uiState = uiState,
                onPhotoClick = {
                    viewModel.handleEvent(
                        PhotoFeedContract.Event.PhotoDetailsOpenedEvent(
                            it
                        )
                    )
                },
                onLikeClick = { viewModel.handleEvent(PhotoFeedContract.Event.PhotoLikedEvent(it)) },
                onRemoveLikeClick = {
                    viewModel.handleEvent(
                        PhotoFeedContract.Event.PhotoUnlikedEvent(
                            it
                        )
                    )
                },
                onSearchClick = { viewModel.handleEvent(PhotoFeedContract.Event.PhotoSearchClickedEvent) },
                onLoadError = { viewModel.handleEvent(PhotoFeedContract.Event.LoadingErrorEvent) },
                onSaveScrollState = { scrollState ->
                    viewModel.handleEvent(PhotoFeedContract.Event.ScrollStateSavedEvent(
                        scrollState = scrollState
                    ))
                },
                onCloseFieldClick = { viewModel.handleEvent(PhotoFeedContract.Event.FieldClosedEvent) },
                onPagingCloseField = { viewModel.handleEvent(PhotoFeedContract.Event.PagingFieldClosedEvent) },
                onPagingRetry = { viewModel.handleEvent(PhotoFeedContract.Event.PagingRetryEvent(it)) })
        }
    }
}