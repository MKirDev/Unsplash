package com.mkirdev.unsplash.services

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.provider.MediaStore
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import com.mkirdev.unsplash.core.ui.R

private const val CHANNEL_ID = "download_channel"
private const val DOWNLOAD_SUCCESS = "Photo uploaded"

class PhotoDownloadService : LifecycleService() {

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        startForegroundService()
        return START_NOT_STICKY
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    @SuppressLint("IntentReset")
    private fun startForegroundService() {

        val contentIntent = PendingIntent.getActivity(
            this,
            0,
            Intent(Intent.ACTION_VIEW).apply {
                setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                setType("image/jpeg")
                setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            },
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_download)
            .setContentTitle(DOWNLOAD_SUCCESS)
            .setContentTitle("Photo uploaded success")
            .setContentIntent(contentIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        createNotificationChannel()

        startForeground(1, notification)
    }

    private fun createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                getString(R.string.downloads_channel),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = getString(R.string.description_channel)
                setSound(null, null)
            }
            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

}

