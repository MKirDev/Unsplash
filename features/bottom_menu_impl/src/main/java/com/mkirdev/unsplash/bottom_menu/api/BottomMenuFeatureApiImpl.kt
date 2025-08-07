package com.mkirdev.unsplash.bottom_menu.api

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.mkirdev.unsplash.bottom_menu.di.BottomMenuDependenciesProvider
import com.mkirdev.unsplash.bottom_menu.di.DaggerBottomMenuComponent
import com.mkirdev.unsplash.bottom_menu.impl.BottomMenuScreenWrapper
import com.mkirdev.unsplash.bottom_menu.impl.BottomMenuViewModel
import com.mkirdev.unsplash.bottom_menu.api.navigation.BottomMenuDestination
import javax.inject.Inject

class BottomMenuFeatureApiImpl @Inject constructor() : BottomMenuFeatureApi {
    override fun NavHostController.navigateToBottomMenu() {
        popBackStack()
        navigate(BottomMenuDestination.route)
    }

    override fun NavGraphBuilder.bottomMenu(
        onNavigateToPhotoDetails: (String) -> Unit,
        onNavigateToCollectionDetails: (String) -> Unit,
        onLogout: () -> Unit
    ) {
        navigation(
            startDestination = BottomMenuDestination.route,
            route = BottomMenuDestination.graph
        ) {
            bottomMenuInner(
                onNavigateToPhotoDetails = onNavigateToPhotoDetails,
                onNavigateToCollectionDetails = onNavigateToCollectionDetails,
                onLogout = onLogout
            )
        }
    }

    private fun NavGraphBuilder.bottomMenuInner(
        onNavigateToPhotoDetails: (String) -> Unit,
        onNavigateToCollectionDetails: (String) -> Unit,
        onLogout: () -> Unit
    ) {
        composable(route = BottomMenuDestination.route) {

            val bottomMenuComponent by remember {
                mutableStateOf(
                    DaggerBottomMenuComponent.builder()
                        .addDependencies(BottomMenuDependenciesProvider.dependencies)
                        .build()
                )
            }

            val viewModel: BottomMenuViewModel = viewModel(
                factory = bottomMenuComponent.bottomMenuViewModelFactory
            )

            val photoFeedFeatureApi by remember {
                mutableStateOf(bottomMenuComponent.photoFeedFeatureApi)
            }

            val collectionsFeatureApi by remember {
                mutableStateOf(bottomMenuComponent.collectionsFeatureApi)
            }

            val profileFeatureApi by remember {
                mutableStateOf(bottomMenuComponent.profileFeatureApi)
            }

            BottomMenuScreenWrapper(
                viewModel = viewModel,
                photoFeedFeatureApi = photoFeedFeatureApi,
                collectionsFeatureApi = collectionsFeatureApi,
                profileFeatureApi = profileFeatureApi,
                onPhotoSelected = onNavigateToPhotoDetails,
                onCollectionSelected = onNavigateToCollectionDetails,
                onLogout = onLogout
            )
        }
    }

}