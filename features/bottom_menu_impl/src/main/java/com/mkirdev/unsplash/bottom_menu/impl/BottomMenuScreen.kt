package com.mkirdev.unsplash.bottom_menu.impl

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mkirdev.unsplash.bottom_menu.widgets.CustomNavigationBar
import com.mkirdev.unsplash.collections.api.CollectionsFeatureApi
import com.mkirdev.unsplash.core.contract.viewmodel.applyEffect
import com.mkirdev.unsplash.core.navigation.navigateSingleDestinationTo
import com.mkirdev.unsplash.photo_feed.api.PhotoFeedFeatureApi
import com.mkirdev.unsplash.photo_feed.api.navigation.PhotoFeedTopLevelDestination

@Composable
fun BottomMenuScreenWrapper(
    viewModel: BottomMenuViewModel,
    photoFeedFeatureApi: PhotoFeedFeatureApi,
    collectionsFeatureApi: CollectionsFeatureApi,
    onPhotoSelected: (String) -> Unit,
    onCollectionSelected: (String) -> Unit,
    onLogout: () -> Unit
) {

    val navController: NavHostController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    viewModel.applyEffect(function = { effect ->
        when (effect) {
            is BottomMenuContract.Effect.TopLevelDestination -> {
                navController.navigateSingleDestinationTo(effect.route)
            }
            null -> Unit
        }
    })

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    BottomMenuScreen(
        uiState = uiState,
        photoFeedFeatureApi = photoFeedFeatureApi,
        collectionsFeatureApi = collectionsFeatureApi,
        navController = navController,
        currentRoute = currentRoute,
        onPhotoSelected = onPhotoSelected,
        onCollectionSelected = onCollectionSelected,
        onLogout = onLogout
    )
}

@Composable
private fun BottomMenuScreen(
    uiState: BottomMenuContract.State,
    photoFeedFeatureApi: PhotoFeedFeatureApi,
    collectionsFeatureApi: CollectionsFeatureApi,
    navController: NavHostController,
    currentRoute: String?,
    onPhotoSelected: (String) -> Unit,
    onCollectionSelected: (String) -> Unit,
    onLogout: () -> Unit
) {
    when (uiState) {
        BottomMenuContract.State.Idle -> {}
        is BottomMenuContract.State.Success -> {

            Scaffold(
                bottomBar = {
                    CustomNavigationBar(
                        currentDestination = currentRoute,
                        destinations = uiState.topLevelDestination,
                        onNavigateToTopLevel = { route ->
                            navController.navigateSingleDestinationTo(route = route)
                        },
                        modifier = Modifier.zIndex(0f)
                    )
                }
            ) { innerPadding ->
                Box(modifier = Modifier
                    .padding(
                        top = innerPadding.calculateTopPadding(),
                        bottom = innerPadding.calculateTopPadding()
                    )
                    .padding(horizontal = innerPadding.calculateTopPadding())
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = PhotoFeedTopLevelDestination.route
                    ) {
                        with(photoFeedFeatureApi) {
                            photoFeed(
                                onNavigateToDetails = onPhotoSelected
                            )
                        }

                        with(collectionsFeatureApi) {
                            collections(
                                onNavigateToCollectionDetails = onCollectionSelected
                            )
                        }
                    }
                }
            }
        }
    }

}