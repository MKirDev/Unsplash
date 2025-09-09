package com.mkirdev.unsplash.bottom_menu.impl

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.mkirdev.unsplash.bottom_menu.widgets.CustomTopBar
import com.mkirdev.unsplash.collections.api.CollectionsFeatureApi
import com.mkirdev.unsplash.core.contract.viewmodel.applyEffect
import com.mkirdev.unsplash.core.navigation.navigateSingleDestinationTo
import com.mkirdev.unsplash.photo_explore.api.PhotoExploreFeatureApi
import com.mkirdev.unsplash.photo_explore.api.navigation.PhotoExploreTopLevelDestination
import com.mkirdev.unsplash.profile.api.ProfileFeatureApi

@Composable
fun BottomMenuScreenWrapper(
    viewModel: BottomMenuViewModel,
    photoExploreFeatureApi: PhotoExploreFeatureApi,
    collectionsFeatureApi: CollectionsFeatureApi,
    profileFeatureApi: ProfileFeatureApi,
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
        photoExploreFeatureApi = photoExploreFeatureApi,
        collectionsFeatureApi = collectionsFeatureApi,
        profileFeatureApi = profileFeatureApi,
        navController = navController,
        currentRoute = currentRoute,
        onPhotoSelected = onPhotoSelected,
        onCollectionSelected = onCollectionSelected,
        onLogout = onLogout
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun BottomMenuScreen(
    uiState: BottomMenuContract.State,
    photoExploreFeatureApi: PhotoExploreFeatureApi,
    collectionsFeatureApi: CollectionsFeatureApi,
    profileFeatureApi: ProfileFeatureApi,
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
                modifier = Modifier,
                bottomBar = {
                    CustomNavigationBar(
                        currentDestination = currentRoute,
                        iconicDestinations = uiState.iconicTopDestinations,
                        onNavigateToTopLevel = { route ->
                            navController.navigateSingleDestinationTo(route = route)
                        },
                        modifier = Modifier.zIndex(0f)
                    )
                }
            ) { innerPadding ->
                Box(modifier = Modifier.padding(top = innerPadding.calculateTopPadding())) {
                    NavHost(
                        navController = navController,
                        modifier = Modifier.zIndex(1f),
                        startDestination = PhotoExploreTopLevelDestination.route,
                    ) {

                        with(photoExploreFeatureApi) {
                            photoExplore(
                                onNavigateToDetails = onPhotoSelected,
                            )
                        }

                        with(collectionsFeatureApi) {
                            collections(
                                onNavigateToCollectionDetails = onCollectionSelected
                            )
                        }

                        with(profileFeatureApi) {
                            profile(
                                onLogout = onLogout,
                                onNavigateToDetails = onPhotoSelected
                            )
                        }
                    }
                    CustomTopBar(
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            }

        }
    }
}