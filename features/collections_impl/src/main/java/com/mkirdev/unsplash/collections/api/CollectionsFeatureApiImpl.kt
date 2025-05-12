package com.mkirdev.unsplash.collections.api

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mkirdev.unsplash.collections.impl.CollectionsContract
import com.mkirdev.unsplash.collections.impl.CollectionsScreen
import com.mkirdev.unsplash.collections.impl.CollectionsViewModel
import com.mkirdev.unsplash.core.contract.viewmodel.applyEffect
import com.mkirdev.unsplash.core.navigation.ProjectNavDestination

class CollectionsFeatureApiImpl : CollectionsFeatureApi {
    override fun NavGraphBuilder.collections(onNavigateToCollectionDetails: (String) -> Unit) {
        composable(route = CollectionsDestination.route) {
            val viewModel: CollectionsViewModel = viewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            viewModel.applyEffect(function = { effect ->
                when (effect) {
                    is CollectionsContract.Effect.CollectionDetails -> {
                        onNavigateToCollectionDetails(effect.collectionId)
                    }

                    null -> Unit
                }
            })

            CollectionsScreen(
                uiState = uiState,
                onCollectionClick = { viewModel.handleEvent(CollectionsContract.Event.CollectionDetailsEvent(it)) },
                onLoadError = { viewModel.handleEvent(CollectionsContract.Event.ErrorLoadEvent) },
                onCloseFieldClick = { viewModel.handleEvent(CollectionsContract.Event.FieldCloseEvent) }
            )
        }
    }
}

object CollectionsDestination : ProjectNavDestination {
    override val route: String = "collections"
}