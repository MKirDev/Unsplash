package com.mkirdev.unsplash.auth.api.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.mkirdev.unsplash.core.navigation.ProjectNavDestination

object AuthDestination : ProjectNavDestination {
    const val codeArg = "code"
    override val route: String = "auth?code={$codeArg}"
    val routeWithArgs = "$route/{$codeArg}"
    val arguments = listOf(navArgument(codeArg) {
        type = NavType.StringType
        nullable = true
    })
    fun deeplink(schema: String, host: String, path: String) = listOf(navDeepLink {
        uriPattern = "$schema://$host$path?code={${codeArg}}"
    })
}