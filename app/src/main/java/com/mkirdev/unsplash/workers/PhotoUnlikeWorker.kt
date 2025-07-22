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
import kotlinx.coroutines.withContext

private const val WORKER_TAG = "PHOTO.UNLIKE.WORKER"
private const val WORKER_NAME = "UNLIKED PHOTO"
private const val DATA = "DATA"
private const val EMPTY_ID = ""

class PhotoUnlikeWorker(
    private val context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    private val unlikePhotoRemoteUseCase = DaggerProvider.appComponent.unlikePhotoRemoteUseCase
    private val dispatcher = DaggerProvider.appComponent.coroutineDispatcher

    override suspend fun doWork(): Result {
        return withContext(dispatcher) {
            try {
                val photoId = inputData.getString(DATA) ?: EMPTY_ID
                unlikePhotoRemoteUseCase.execute(photoId)
                Result.success()
            } catch (e: Exception) {
                Result.retry()
            }
        }
    }

    companion object {
        private val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        private fun createWorkRequest(photoId: String): OneTimeWorkRequest {
            return OneTimeWorkRequestBuilder<PhotoUnlikeWorker>()
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