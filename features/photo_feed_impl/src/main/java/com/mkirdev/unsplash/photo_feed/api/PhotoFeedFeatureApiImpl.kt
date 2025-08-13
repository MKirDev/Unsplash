package com.mkirdev.unsplash.photo_feed.api

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mkirdev.unsplash.core.contract.viewmodel.applyEffect
import com.mkirdev.unsplash.photo_feed.di.DaggerPhotoFeedComponent
import com.mkirdev.unsplash.photo_feed.di.PhotoFeedDependenciesProvider
import com.mkirdev.unsplash.photo_feed.impl.PhotoFeedContract
import com.mkirdev.unsplash.photo_feed.impl.PhotoFeedScreenWrapper
import com.mkirdev.unsplash.photo_feed.impl.PhotoFeedViewModel
import com.mkirdev.unsplash.photo_feed.api.navigation.PhotoFeedTopLevelDestination
import javax.inject.Inject

class PhotoFeedFeatureApiImpl @Inject constructor(): PhotoFeedFeatureApi {
    override fun NavGraphBuilder.photoFeed(onNavigateToDetails: (String) -> Unit) {
        composable(route = PhotoFeedTopLevelDestination.route) {

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
                    is PhotoFeedContract.Effect.Details -> { onNavigateToDetails(effect.photoId) }
                    null -> Unit
                }
            })

            PhotoFeedScreenWrapper(
                uiState = uiState,
                onSearch = { viewModel.handleEvent(PhotoFeedContract.Event.SearchEvent(it)) },
                onPhotoClick = { viewModel.handleEvent(PhotoFeedContract.Event.PhotoDetailsOpenedEvent(it)) },
                onLikeClick = { viewModel.handleEvent(PhotoFeedContract.Event.PhotoLikedEvent(it)) },
                onRemoveLikeClick = { viewModel.handleEvent(PhotoFeedContract.Event.PhotoUnlikedEvent(it)) },
                onLoadError = { viewModel.handleEvent(PhotoFeedContract.Event.LoadingErrorEvent) },
                onCloseFieldClick = { viewModel.handleEvent(PhotoFeedContract.Event.FieldClosedEvent) },
                onPagingCloseField = { viewModel.handleEvent(PhotoFeedContract.Event.PagingFieldClosedEvent) },
                onPagingRetry = { viewModel.handleEvent(PhotoFeedContract.Event.PagingRetryEvent(it)) })
        }
    }
}