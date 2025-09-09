package com.mkirdev.unsplash.photo_explore.impl

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.mkirdev.unsplash.photo_feed.api.PhotoFeedFeatureApi
import com.mkirdev.unsplash.photo_feed.api.navigation.PhotoFeedDestination
import com.mkirdev.unsplash.photo_search.api.PhotoSearchFeatureApi

@Composable
internal fun PhotoExploreScreenWrapper(
    photoFeedFeatureApi: PhotoFeedFeatureApi,
    photoSearchFeatureApi: PhotoSearchFeatureApi,
    onPhotoSelected: (String) -> Unit
) {
    val navController: NavHostController = rememberNavController()

    PhotoExploreScreen(
        photoFeedFeatureApi = photoFeedFeatureApi,
        photoSearchFeatureApi = photoSearchFeatureApi,
        navController = navController,
        onPhotoSelected = onPhotoSelected
    )
}

@Composable
private fun PhotoExploreScreen(
    photoFeedFeatureApi: PhotoFeedFeatureApi,
    photoSearchFeatureApi: PhotoSearchFeatureApi,
    navController: NavHostController,
    onPhotoSelected: (String) -> Unit
) {
    Box(Modifier.fillMaxSize()) {
        Column {
            NavHost(
                navController = navController,
                startDestination = PhotoFeedDestination.route,
            ) {
                with(photoFeedFeatureApi) {
                    photoFeed(
                        onNavigateToDetails = onPhotoSelected,
                        onNavigateToSearch = {
                            with(photoSearchFeatureApi) {
                                navController.navigateToSearch()
                            }
                        }
                    )
                }

                with(photoSearchFeatureApi) {
                    photoSearch(
                        onNavigateToDetails = onPhotoSelected,
                        onNavigateToFeed = {
                            with(photoFeedFeatureApi) {
                                navController.navigateToFeed()
                            }
                        }
                    )
                }
            }
        }
    }

}