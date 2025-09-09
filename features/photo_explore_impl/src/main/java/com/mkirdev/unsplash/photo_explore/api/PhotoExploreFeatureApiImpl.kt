package com.mkirdev.unsplash.photo_explore.api

import androidx.compose.runtime.remember
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mkirdev.unsplash.photo_explore.api.navigation.PhotoExploreTopLevelDestination
import com.mkirdev.unsplash.photo_explore.di.DaggerPhotoExploreComponent
import com.mkirdev.unsplash.photo_explore.di.PhotoExploreDependenciesProvider
import com.mkirdev.unsplash.photo_explore.impl.PhotoExploreScreenWrapper
import javax.inject.Inject

class PhotoExploreFeatureApiImpl @Inject constructor() : PhotoExploreFeatureApi {
    override fun NavGraphBuilder.photoExplore(
        onNavigateToDetails: (String) -> Unit,
    ) {
        composable(route = PhotoExploreTopLevelDestination.route) {

            val photoExploreComponent = remember {
                DaggerPhotoExploreComponent.builder()
                    .addDependencies(PhotoExploreDependenciesProvider.dependencies)
                    .build()
            }

            val photoFeedFeatureApi = remember {
                photoExploreComponent.photoFeedFeatureApi
            }

            val photoSearchFeatureApi = remember {
                photoExploreComponent.photoSearchFeatureApi
            }

            PhotoExploreScreenWrapper(
                photoFeedFeatureApi = photoFeedFeatureApi,
                photoSearchFeatureApi = photoSearchFeatureApi,
                onPhotoSelected = onNavigateToDetails
            )
        }
    }
}