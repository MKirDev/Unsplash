package com.mkirdev.unsplash.auth.api.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.mkirdev.unsplash.core.navigation.ProjectNavDestination

object AuthDestination : ProjectNavDestination {
    const val codeArg = "code"
    const val errorArg = "error"
    override val route: String = "auth"
    val routeWithArgs = "$route?code={$codeArg}&error={$errorArg}"
    val arguments = listOf(
        navArgument(codeArg) {
            type = NavType.StringType
            nullable = true
            defaultValue = ""
        },
        navArgument(errorArg) {
            type = NavType.BoolType
            nullable = false
            defaultValue = false
        }
    )

    fun deeplink(schema: String, host: String, path: String) = listOf(navDeepLink {
        uriPattern = "$schema://$host$path?code={${codeArg}}"
    })
}