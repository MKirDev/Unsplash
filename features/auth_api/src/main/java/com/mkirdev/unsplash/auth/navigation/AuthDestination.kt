package com.mkirdev.unsplash.auth.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.mkirdev.unsplash.core.navigation.ProjectNavDestination

object AuthDestination : ProjectNavDestination {
    const val codeArg = "code"
    override val route: String = "auth?code={$codeArg}"
    val routeWithArgs = "$route/{$codeArg}"
    val arguments = listOf(navArgument(codeArg) { type = NavType.StringType })
    fun deeplink(schema: String) = listOf(navDeepLink {
        uriPattern = "$schema://redirect.com/callback?code={${codeArg}}"
    })
}