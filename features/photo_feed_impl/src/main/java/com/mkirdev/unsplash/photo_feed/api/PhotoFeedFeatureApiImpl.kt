package com.mkirdev.unsplash.photo_feed.api

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mkirdev.unsplash.core.contract.viewmodel.applyEffect
import com.mkirdev.unsplash.photo_feed.impl.PhotoFeedContract
import com.mkirdev.unsplash.photo_feed.impl.PhotoFeedScreen
import com.mkirdev.unsplash.photo_feed.impl.PhotoFeedViewModel
import com.mkirdev.unsplash.photo_feed.navigation.PhotoFeedTopLevelDestination
import javax.inject.Inject

class PhotoFeedFeatureApiImpl @Inject constructor(): PhotoFeedFeatureApi {
    override fun NavGraphBuilder.photoFeed(onNavigateToDetails: (String) -> Unit) {
        composable(route = PhotoFeedTopLevelDestination.route) {
            val viewModel: PhotoFeedViewModel = viewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            viewModel.applyEffect(
                function = { effect ->
                when (effect) {
                    is PhotoFeedContract.Effect.Details -> { onNavigateToDetails(effect.photoId) }
                    null -> Unit
                }
            })

            PhotoFeedScreen(
                uiState = uiState,
                onSearch = { viewModel.handleEvent(PhotoFeedContract.Event.SearchEvent(it)) },
                onPhotoClick = { viewModel.handleEvent(PhotoFeedContract.Event.PhotoDetailsOpenedEvent(it)) },
                onLikeClick = { viewModel.handleEvent(PhotoFeedContract.Event.PhotoLikedEvent(it)) },
                onRemoveLikeClick = { viewModel.handleEvent(PhotoFeedContract.Event.PhotoLikedEvent(it)) },
                onLoadPhotosClick = { viewModel.handleEvent(PhotoFeedContract.Event.PhotosLoadingRequestedEvent) },
                onCloseFieldClick = { viewModel.handleEvent(PhotoFeedContract.Event.FieldClosedEvent) })
        }
    }
}