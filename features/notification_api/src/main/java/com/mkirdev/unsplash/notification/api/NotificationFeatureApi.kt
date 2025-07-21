package com.mkirdev.unsplash.notification.api

import androidx.compose.runtime.Composable

interface NotificationFeatureApi {
    @Composable
    fun NotificationsFeature(text: String)

    @Composable
    fun SettingsFeature(text: String)
}