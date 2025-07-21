package com.mkirdev.unsplash.core.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.core.net.toUri

object ExternalIntentHelper {
    fun startMapIntent(context: Context, latitude: Double, longitude: Double) {
        val uri = "https://maps.google.com/maps?q=" +
                "${latitude}," +
                "$longitude"
        val intent = Intent(Intent.ACTION_VIEW, uri.toUri())
            .setPackage("com.google.android.apps.maps")
            .addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT)
        context.startActivity(intent)
    }

    fun startShareIntent(context: Context, link: String) {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(
                Intent.EXTRA_TEXT,
                "$link\n"
            )
        }
        val intent = Intent
            .createChooser(shareIntent, "Share")
            .addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT)
        context.startActivity(intent)
    }

    fun startSettingsIntent(context: Context) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", context.packageName, null)
            addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT)
        }
        context.startActivity(intent)
    }
}