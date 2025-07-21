package com.mkirdev.unsplash.core.utils

import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.core.content.ContextCompat

object PermissionChecker {
    fun checkPermission(
        context: Context,
        permission: String,
        onGranted: () -> Unit,
        launcher: ManagedActivityResultLauncher<String, Boolean>
    ) {
        when {
            ContextCompat.checkSelfPermission(
                context, permission
            ) == PackageManager.PERMISSION_GRANTED -> {
                onGranted()
            }

            else -> {
                launcher.launch(permission)
            }
        }
    }
}