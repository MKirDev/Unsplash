package com.mkirdev.unsplash.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.WorkManager
import com.mkirdev.unsplash.workers.CacheCleanWorker

class CacheCleanScheduleReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context ?: return
        val workManager: WorkManager = WorkManager.getInstance(context)
        CacheCleanWorker.enqueueUniqueWork(workManager)
    }
}