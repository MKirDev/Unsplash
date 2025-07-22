package com.mkirdev.unsplash.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.mkirdev.unsplash.di.DaggerProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class ScheduleRebootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            val cacheScheduler = DaggerProvider.appComponent.cacheScheduler
            val dispatcher = DaggerProvider.appComponent.coroutineDispatcher
            val getScheduleFlagUseCase = DaggerProvider.appComponent.getScheduleFlagUseCase

            CoroutineScope(dispatcher).launch {
                val flag = getScheduleFlagUseCase.execute().firstOrNull() ?: false
                if (flag) {
                    cacheScheduler.schedule()
                }
            }
        }
    }
}