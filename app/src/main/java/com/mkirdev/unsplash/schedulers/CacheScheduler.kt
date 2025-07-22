package com.mkirdev.unsplash.schedulers

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.mkirdev.unsplash.receivers.CacheCleanScheduleReceiver

private const val ALARM_CODE = 1000

class CacheScheduler(private val context: Context) {

    private val alarmManager: AlarmManager by lazy {
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }

    private val alarmIntent: PendingIntent
        get() = Intent(context, CacheCleanScheduleReceiver::class.java).let { intent ->
            var pendingIntent: PendingIntent? = null
            pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                PendingIntent.getBroadcast(
                    context,
                    ALARM_CODE,
                    intent,
                    PendingIntent.FLAG_MUTABLE
                )
            } else {
                PendingIntent.getBroadcast(
                    context,
                    ALARM_CODE,
                    intent,
                    PendingIntent.FLAG_ONE_SHOT
                )
            }
            pendingIntent
        }

    fun schedule() {
        val scheduleTimer = System.currentTimeMillis() + AlarmManager.INTERVAL_HOUR
        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            scheduleTimer,
            alarmIntent
        )
    }

    fun cancel() {
        if (Build.VERSION.SDK_INT >= 34) {
            alarmManager.cancelAll()
        } else {
            alarmManager.cancel(alarmIntent)
        }
    }

}