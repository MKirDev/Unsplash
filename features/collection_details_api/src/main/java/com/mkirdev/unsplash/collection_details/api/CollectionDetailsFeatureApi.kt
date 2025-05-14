package com.mkirdev.unsplash.collection_details.api

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder

interface CollectionDetailsFeatureApi {
    fun NavController.navigateToCollectionDetails(collectionId: String)

    fun NavGraphBuilder.collectionDetails(
        onNavigateToDetails: (String) -> Unit,
        onNavigateUp: () -> Unit,
        onNavigateBack: () -> Unit
    )
}