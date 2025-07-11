package com.mkirdev.unsplash.details.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.mkirdev.unsplash.core.navigation.ProjectNavDestination


object DetailsDestination : ProjectNavDestination {
    const val argumentName = "photoId"
    override val route: String = "details"
    val routeWithArgument = "${route}/{${argumentName}}"
    val arguments = listOf(navArgument(argumentName) { type = NavType.StringType })
}