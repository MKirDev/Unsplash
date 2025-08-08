package com.mkirdev.unsplash.workers

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.core.net.ConnectivityManagerCompat
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

private const val WORKER_TAG = "PHOTO.LIKE.WORKER"
private const val WORKER_NAME = "LIKED PHOTO"
private const val DATA = "DATA"
private const val EMPTY_ID = ""

class PhotoLikeWorker(
    private val context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    private val likePhotoRemoteUseCase = DaggerProvider.appComponent.likePhotoRemoteUseCase
    private val dispatcher = DaggerProvider.appComponent.coroutineDispatcher

    override suspend fun doWork(): Result {
        return withContext(dispatcher) {
            try {
                if (isNetworkAvailable(context)) {
                    val photoId = inputData.getString(DATA) ?: EMPTY_ID
                    likePhotoRemoteUseCase.execute(photoId)
                    Result.success()
                } else {
                    Result.retry()
                }
            } catch (e: Exception) {
                Result.retry()
            }
        }
    }

    companion object {
        private val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
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

        private fun isNetworkAvailable(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = connectivityManager.activeNetwork
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
            return capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
        }
    }
}