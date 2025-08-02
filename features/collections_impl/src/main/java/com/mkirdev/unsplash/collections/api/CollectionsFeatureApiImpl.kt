package com.mkirdev.unsplash.collections.api

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mkirdev.unsplash.collections.api.navigation.CollectionsTopLevelDestination
import com.mkirdev.unsplash.collections.di.CollectionsDependenciesProvider
import com.mkirdev.unsplash.collections.di.DaggerCollectionsComponent
import com.mkirdev.unsplash.collections.impl.CollectionsContract
import com.mkirdev.unsplash.collections.impl.CollectionsScreen
import com.mkirdev.unsplash.collections.impl.CollectionsViewModel
import com.mkirdev.unsplash.core.contract.viewmodel.applyEffect
import javax.inject.Inject

class CollectionsFeatureApiImpl @Inject constructor(): CollectionsFeatureApi {
    override fun NavGraphBuilder.collections(onNavigateToCollectionDetails: (String) -> Unit) {
        composable(route = CollectionsTopLevelDestination.route) {

            val collectionsComponent by remember {
                mutableStateOf(
                    DaggerCollectionsComponent.builder()
                        .addDependencies(CollectionsDependenciesProvider.dependencies)
                        .build()
                )
            }

            val viewModel: CollectionsViewModel = viewModel(
                factory = collectionsComponent.collectionsViewModelFactory
            )

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
                onCollectionClick = { viewModel.handleEvent(CollectionsContract.Event.CollectionDetailsOpenedEvent(it)) },
                onLoadError = { viewModel.handleEvent(CollectionsContract.Event.LoadingErrorEvent) },
                onCloseFieldClick = { viewModel.handleEvent(CollectionsContract.Event.FieldClosedEvent) }
            )
        }
    }
}