package com.mkirdev.unsplash.workers

import android.content.Context
import android.util.Log
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.mkirdev.unsplash.di.DaggerProvider
import kotlinx.coroutines.withContext

private const val WORKER_TAG = "CACHE.CLEAN.WORKER"
private const val WORKER_NAME = "CLEANED CACHE"

class CacheCleanWorker(
    private val context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    private val cacheScheduler = DaggerProvider.appComponent.cacheScheduler
    private val dispatcher = DaggerProvider.appComponent.coroutineDispatcher

    override suspend fun doWork(): Result {
        return withContext(dispatcher) {
            try {
                context.cacheDir?.let {
                    if (it.exists()) it.deleteRecursively()
                }
                cacheScheduler.schedule()
                Result.success()
            } catch (e: Exception) {
                e.stackTrace
                Result.failure()
            }
        }
    }

    companion object {
        private val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        private fun createWorkRequest(): OneTimeWorkRequest {
            return OneTimeWorkRequestBuilder<CacheCleanWorker>()
                .addTag(WORKER_TAG)
                .setConstraints(constraints)
                .build()
        }

        internal fun enqueueUniqueWork(workManager: WorkManager) {
            val workRequest = createWorkRequest()
            workManager.enqueueUniqueWork(WORKER_NAME, ExistingWorkPolicy.REPLACE, workRequest)
        }
    }
}