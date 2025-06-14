package com.mkirdev.unsplash.core.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

interface ProjectNavDestination {
    val route: String
}

interface TopLevelDestination : ProjectNavDestination {
    @get:DrawableRes
    val iconId: Int
    @get:StringRes
    val titleId: Int
}

fun NavHostController.navigateSingleDestinationTo(route: String) {
    navigate(route) {
        popUpTo(this@navigateSingleDestinationTo.graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}