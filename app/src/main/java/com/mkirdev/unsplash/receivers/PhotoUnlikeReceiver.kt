package com.mkirdev.unsplash.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.WorkManager
import com.mkirdev.unsplash.workers.PhotoUnlikeWorker

private const val DATA_EXTRA = "EXTRA"
class PhotoUnlikeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context ?: return

        val workManager = WorkManager.getInstance(context)

        val photoId = intent?.getStringExtra(DATA_EXTRA)

        PhotoUnlikeWorker.enqueueUniqueWork(workManager, photoId)
    }
}