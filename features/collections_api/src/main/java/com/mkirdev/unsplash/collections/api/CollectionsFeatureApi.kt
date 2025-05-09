package com.mkirdev.unsplash.collections.api

import androidx.navigation.NavGraphBuilder

interface CollectionsFeatureApi {
    fun NavGraphBuilder.collections(onNavigateToCollectionDetails: (String) -> Unit)
}