package com.mkirdev.unsplash.workers

import android.content.Context
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.mkirdev.unsplash.di.DaggerProvider

private const val WORKER_TAG = "PHOTO.LIKE.WORKER"
private const val WORKER_NAME = "LIKED PHOTO"
private const val DATA = "DATA"
private const val EMPTY_ID = ""
class PhotoLikeWorker(
    private val context: Context,
    workerParams: WorkerParameters) : CoroutineWorker(context, workerParams) {

    private val photosApi = DaggerProvider.appComponent.photosApi
    override suspend fun doWork(): Result {
        return try {
            val photoId = inputData.getString(DATA) ?: EMPTY_ID
            photosApi.likePhoto(photoId)
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }

    companion object {
        private val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        private fun createWorkRequest(photoId: String): OneTimeWorkRequest {
            return OneTimeWorkRequestBuilder<PhotoLikeWorker>()
                .setInputData(workDataOf(DATA to photoId))
                .addTag(WORKER_TAG)
                .setConstraints(constraints)
                .build()
        }

        internal fun enqueueUniqueWork(workManager: WorkManager, photoId: String?) {
            val workRequest = createWorkRequest(photoId ?: EMPTY_ID)
            workManager.enqueueUniqueWork(WORKER_NAME, ExistingWorkPolicy.REPLACE, workRequest)
        }
    }
}