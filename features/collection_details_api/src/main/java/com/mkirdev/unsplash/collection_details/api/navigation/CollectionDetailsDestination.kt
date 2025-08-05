package com.mkirdev.unsplash.collection_details.api.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.mkirdev.unsplash.core.navigation.ProjectNavDestination

object CollectionDetailsDestination : ProjectNavDestination {
    const val argumentName = "collectionId"
    override val route: String = "collection_details"
    val routeWithArgument = "${route}/{${argumentName}}"
    val arguments = listOf(navArgument(argumentName) { type = NavType.StringType })
}