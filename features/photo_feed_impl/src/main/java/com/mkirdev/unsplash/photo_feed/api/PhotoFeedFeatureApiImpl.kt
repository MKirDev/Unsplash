package com.mkirdev.unsplash.photo_feed.api

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mkirdev.unsplash.core.contract.viewmodel.applyEffect
import com.mkirdev.unsplash.core.navigation.ProjectNavDestination
import com.mkirdev.unsplash.photo_feed.impl.PhotoFeedContract
import com.mkirdev.unsplash.photo_feed.impl.PhotoFeedScreen
import com.mkirdev.unsplash.photo_feed.impl.PhotoFeedViewModel

class PhotoFeedFeatureApiImpl : PhotoFeedFeatureApi {
    override fun NavGraphBuilder.photoFeed(onNavigateToDetails: (String) -> Unit) {
        composable(route = PhotoFeedDestination.route) {
            val viewModel: PhotoFeedViewModel = viewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            viewModel.applyEffect(
                function = { effect ->
                when (effect) {
                    is PhotoFeedContract.Effect.Details -> { onNavigateToDetails(effect.photoId) }
                    PhotoFeedContract.Effect.BackPressed -> Unit
                    null -> Unit
                }
            })

            PhotoFeedScreen(
                uiState = uiState,
                onSearch = { viewModel.handleEvent(PhotoFeedContract.Event.SearchEvent(it)) },
                onClickPhoto = { viewModel.handleEvent(PhotoFeedContract.Event.PhotoClickEvent(it)) },
                onLike = { viewModel.handleEvent(PhotoFeedContract.Event.PhotoLikeEvent(it)) },
                onRemoveLike = { viewModel.handleEvent(PhotoFeedContract.Event.PhotoLikeEvent(it)) },
                onLoadPhotos = { viewModel.handleEvent(PhotoFeedContract.Event.PhotosLoadEvent) },
                onCloseField = { viewModel.handleEvent(PhotoFeedContract.Event.FieldCloseEvent) })
        }
    }
}

object PhotoFeedDestination : ProjectNavDestination {
    override val route: String = "photoFeed"
}