package com.mkirdev.unsplash.notification.api

import androidx.compose.runtime.Composable
import com.mkirdev.unsplash.notification.impl.NotificationScreen
import com.mkirdev.unsplash.notification.impl.SettingsScreen
import javax.inject.Inject

class NotificationFeatureApiImpl @Inject constructor() : NotificationFeatureApi {
    @Composable
    override fun NotificationsFeature(text: String): Unit = NotificationScreen(text)


    @Composable
    override fun SettingsFeature(text: String): Unit = SettingsScreen(text)

}